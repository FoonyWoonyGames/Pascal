package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Properties;

import Item.Item;
import Entity.Npc;
import Entity.Player;
import GUI.GuiFrame;
import GameState.GameStateManager;
import GameState.InGame;
import Item.ItemKey;
import Pascal.Game;
import Pascal.GamePanel;
import Util.Crash;

public class SaveManager {
	// Encrypt

	public SaveManager() {
		
	}
	
	public static void Save(Player player, int savefile) {
		GamePanel.loadingString = "SAVING";
		GamePanel.setLoading(true);
		File dir = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves");
		dir.mkdir();
		

		try (PrintStream out = new PrintStream(
				new FileOutputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves/save" + savefile + ".fwgs"))) {
			String filet = new String();
			filet = "state=" + InGame.state
					+ "\nversion=" + Game.version
					+ "\nzone=" + InGame.getZoneID()
					+ "\nx=" + player.getx()
					+ "\ny=" + player.gety()
					+ "\nhealth=" + player.getHealth()
					+ "\nenergy=" + player.getEnergy()
					+ "\nmoney =" + InGame.cash;
			
			Properties prop = new Properties();
			FileInputStream inputStream = new FileInputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves/save" + savefile + ".fwgs");
			
			prop.load(inputStream);
			
			inputStream.close();
			for (int i=0;i < 35;i++) {
				int j = i+1;
				Item item = player.inventory.slots.get(i).getItem();
				if(item != null) {
					if(item.isKey()) {
						filet = filet + ("\nitem" + j + "=" + item.getID() + "," + item + "," + ((ItemKey) item).getKeyID());
					}
					else {
						filet = filet + ("\nitem" + j + "=" + item.getID() + "," + item.getLocalizedName());
					}
				}
				else {
					filet = filet + ("\nitem" + j + "=0,Air");
				}
			}
			filet = filet + "\nobjectives=";
			for (int i=0;i < InGame.objm.getActiveObjectives();i++) {
				if(i == InGame.objm.getActiveObjectives() - 1) {
					filet = filet + InGame.objm.getActiveObjective(i).getID() + ":" + InGame.objm.getActiveObjective(i).getState();
				} else {
					filet = filet + InGame.objm.getActiveObjective(i).getID() + ":" + InGame.objm.getActiveObjective(i).getState() + ",";
				}
			}
			filet = filet + "\ncomjectives=";
			for (int i=0;i < InGame.completedObjectives.size();i++) {
				if(i == InGame.completedObjectives.size() - 1) {
					filet = filet + InGame.completedObjectives.get(i);
				} else {
					filet = filet + InGame.completedObjectives.get(i) + ",";
				}
			}
			
			// NPC#=state,health,x,y,dead
			for (int i=0;i < Npc.Npcs.size();i++) {
				Npc npc = Npc.Npcs.get(i);
				filet = filet + "\nNPC" + i + "=";
				filet = filet + npc.getState() + "," + npc.getHealth() + "," + npc.getx() + "," + npc.gety() + "," + npc.isDead() + "," + npc.getShortName();
			}
			String updatedText = filet.replaceAll("\n", System.lineSeparator());
			String crypted = Game.cm.encrypt(updatedText);
            out.println(crypted);
            out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		GamePanel.loadingString = "LOADING";
		GamePanel.setLoading(false);
	}
	static String pathString;
	static String pathStringTemp;
	public static String LoadProperty(int savefile, String property) {
		FileInputStream inputStream = null;
		String returnStr = null;
		pathString = System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves/save" + savefile + ".fwgs";
		pathStringTemp = System.getProperty("user.home") + "/AppData/Local/Temp/FoonyWoonyGames_TempSavefile" + savefile + ".fwgs";
		try (PrintStream out = new PrintStream(
				new FileOutputStream(pathStringTemp))){
			Properties prop = new Properties();
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(pathString));
				out.println(Game.cm.decrypt(br.readLine()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			inputStream = new FileInputStream(pathStringTemp);
			
			prop.load(inputStream);

			returnStr = prop.getProperty(property);
			inputStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
		} 
		return returnStr;
	}
	public static void Load(Player player) {
		InGame.loadSavefile(true, "save1");
		GameStateManager.setState(GameStateManager.INGAMESTATE);
		if(LoadProperty(1, "version") == null || !LoadProperty(1, "version").equalsIgnoreCase(Game.version)) {
			GuiFrame frame;
			frame = new GuiFrame("versionproblem");
			frame.setTitle("Savefile is in another version");
			String ver;
			if(LoadProperty(1, "version") == null) {
				ver = "No version (probably from before Alpha 1.0)";
			}
			else {
				ver = LoadProperty(1, "version");
			}
			frame.setText("It looks like this savefile was saved\nin another version. Please note that changes\nand/or problems may occur\nif you play in a newer version.\nSavefile's Version: " + ver);
			frame.setWidth(1000);
			frame.setHeight(300);
			frame.setPosition(960, 260);
			frame.setVisible(true);
			InGame.displayFrame(frame);
		}
		InGame.guiLoadingSavefile.setState(0);
		InGame.state = Integer.parseInt(LoadProperty(1, "state"));
		InGame.setZone(Integer.parseInt(LoadProperty(1, "zone")));
		InGame.guiLoadingSavefile.setState(1);
		InGame.player = new Player(InGame.tileMap);
		int x = Integer.parseInt(LoadProperty(1, "x"));
		int y = Integer.parseInt(LoadProperty(1, "y"));
		InGame.player.setPosition(x, y);
		InGame.player.setHealth(Integer.parseInt(LoadProperty(1, "health")));
		InGame.player.setEnergy(Integer.parseInt(LoadProperty(1, "energy")));
		InGame.guiLoadingSavefile.setState(2);
		InGame.cash = Integer.parseInt(LoadProperty(1, "money"));
		for (int i=1;i < 36;i++) {
			String[] itemStr = LoadProperty(1, "item" + i).split(",");
			Item item = Game.itemRegistry.getItem(Integer.parseInt(itemStr[0]));
			if(Integer.parseInt(itemStr[0]) != 0) {
				item.setLocalizedName(itemStr[1]);
				if(item.isKey()) {
					((ItemKey) item).setKeyID(itemStr[2]);
				}
				InGame.player.inventory.addItem(item);
				item.getSlot().removeItem();
				InGame.player.inventory.slots.get(i-1).setItem(item);
			}
		}
		InGame.guiLoadingSavefile.setState(3);
		String[] objectives = LoadProperty(1, "objectives").split(",");
		for (int i=0;i < objectives.length;i++) {
			String[] obj = objectives[i].split(":");
			InGame.objm.addObjectiveSilent(obj[0]);
			if(InGame.objm.getActiveObjective(obj[0]) != null) {
				InGame.objm.getActiveObjective(obj[0]).setState(Integer.parseInt(obj[1]));
			}
		}
		String[] comjectives = LoadProperty(1, "comjectives").split(",");
		for (int i=0;i < comjectives.length;i++) {
			InGame.completedObjectives.add(comjectives[i]);
		}
		InGame.guiLoadingSavefile.setState(4);
		// NPC#=health,x,y,dead
		for (int i=0;i < Npc.Npcs.size();i++) {
			Npc npc = Npc.Npcs.get(i);
			String[] properties = LoadProperty(1, "NPC" + i).split(",");
			npc.setState(Integer.parseInt(properties[0]));
			npc.setHealth(Integer.parseInt(properties[1]));
			npc.setPosition(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
			if(Boolean.parseBoolean(properties[4])) {
				npc.killNoDrop();
			}
		}
		try {
			File file = new File(pathStringTemp);
			Files.delete(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		InGame.guiLoadingSavefile.setState(5);
		GamePanel.loadingStringWhat = "";
		GamePanel.setLoading(false);
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		InGame.loadSavefile(false, "save1");
	}
}
