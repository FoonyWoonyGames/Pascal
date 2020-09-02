package GUI;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import GameState.InGameLocal;
import Pascal.Game;
import TileMap.Background;
import Util.Crash;
import Util.PAction;
import Util.PColor;

public class GuiLocal extends GuiScreen {
	private Background bg;
	private GuiButton buttonFreeroaming = new GuiButton("local_freeroaming");
	private GuiButton buttonDeathmatch = new GuiButton("local_deathmatch");
	private GuiButton buttonMaps = new GuiButton("local_maps");
	private GuiButton buttonStart = new GuiButton("local_enter");
	private GuiButton buttonPlayers = new GuiButton("local_players");
	private GuiButton buttonBack = new GuiButton("masterback");
	
	private GuiScrollable mapMenu = new GuiScrollable(700, 500, 700, 500);
	private boolean mapMenuVisible = false;
	private ArrayList<GuiButton> mapbuttons = new ArrayList<GuiButton>();

	public static int selectedMode = 0;
	public static int selectedMap = 0;
	
	public static final int MODE_FREEROAM = 0;
	public static final int MODE_DEATHMATCH = 1;
	
	private String[] modes = {
			"Freeroaming",
			"Deahtmatch",
			"Race"
	};
	
	public static ArrayList<String[]> maps;
	
	public GuiLocal() {
		try {
			buttonFreeroaming.setTitle("Freeroaming");
			buttonDeathmatch.setTitle("Deathmatch");
			buttonMaps.setTitle("Change map");
			buttonStart.setTitle("Enter Game");
			buttonPlayers.setTitle("2");
			buttonPlayers.setType(GuiButton.TYPE_BOXHALF);
			buttonBack.setTitle("< Back");
			buttonBack.setType(GuiButton.TYPE_BOXHALF);


			buttonPlayers.setUse(new PAction() {
				@Override
				public void command() {
					if(InGameLocal.playerAmount == 2) {
						InGameLocal.playerAmount = 3;
						buttonPlayers.setTitle("3");
					} else {
						InGameLocal.playerAmount = 2;
						buttonPlayers.setTitle("2");
					}
				}
			});
			buttonMaps.setUse(new PAction() {
				@Override
				public void command() {
					if(mapMenuVisible) {
						buttonMaps.setTitle("Change map");
						mapMenuVisible = false;
					} else {
						buttonMaps.setTitle("Close mapmenu");
						mapMenuVisible = true;
					}
				}
			});
			
			bg = new Background("/textures/background/menu.png", 1);

			
			File folder = new File("../multiplayermaps");
			File[] listOfFiles = folder.listFiles();
			
			maps = new ArrayList<String[]>();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					if(listOfFiles[i].getName().endsWith(".properties")) {
						File mf = new File("../multiplayermaps/" + LoadProperty(listOfFiles[i].getAbsolutePath(), "mapfile"));
						File ts = new File("../multiplayermaps/" + LoadProperty(listOfFiles[i].getAbsolutePath(), "tileset"));
						if(mf.exists() && ts.exists()) {
							String[] map = {
								LoadProperty(listOfFiles[i].getAbsolutePath(), "title"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "mapfile"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "tileset"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "background"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "spawnx01"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "spawny01"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "spawnx02"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "spawny02"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "spawnx03"),
								LoadProperty(listOfFiles[i].getAbsolutePath(), "spawny03")
							};
							maps.add(map);
						}
					}
				}
			}
			for (int i = 0; i < maps.size(); i++) {
				GuiButton b = new GuiButton("local_map_" + maps.get(i)[0]);
				b.setTitle(maps.get(i)[0]);
				int m = i;
				b.setUse(new PAction() {
					@Override
					public void command() {
						selectedMap = m;
					}
				});
				mapbuttons.add(b);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String LoadProperty(String pathString, String property) {
		FileInputStream inputStream = null;
		String returnStr = null;
		try {
			inputStream = new FileInputStream(pathString);
			Properties prop = new Properties();
			prop.load(inputStream);
			
			returnStr = prop.getProperty(property);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
		} 
		return returnStr;
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		g.setFont(new Font("Arial", Font.BOLD, 60));
		guim.drawCenteredColoredOutlinedString("Local Multiplayer", 960, 80, PColor.WHITE, PColor.BLACK, 1, g);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		guim.drawCenteredString("Local Multiplayer is a gamemode in Pascal that lets you play with friends on 1 computer.", 960, 120, g);
		guim.drawCenteredString("Multiple variants of Local Multiplayer is available for you to enjoy.", 960, 150, g);
		guim.drawCenteredString("Up to 3 players can play Local Multiplayer at a time, all using different controls on the keyboard.", 960, 180, g);
		guim.drawCenteredString("Set up your own custom game using the menu below.", 960, 210, g);
		

		g.setColor(PColor.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 35));
		g.drawString("Selected gamemode: " + modes[selectedMode], 50, 380);
		buttonFreeroaming.draw(300, 400, g);
		buttonDeathmatch.draw(300, 500, g);
		g.setColor(PColor.WHITE);
		g.drawString("Selected map: " + maps.get(selectedMap)[0], 50, 750);
		buttonMaps.draw(300, 760, g);
		g.setColor(PColor.WHITE);
		g.drawString("Players in game:", 1575, 970);
		buttonPlayers.draw(1700, 980, g);
		buttonStart.draw(960, 980, g);
		buttonBack.draw(150, 980, g);

		g.setFont(new Font("Arial", Font.BOLD, 50));
		guim.drawCenteredColoredOutlinedString("Controls", 1200, 360, PColor.WHITE, PColor.BLACK, 1, g);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		guim.drawCenteredColoredOutlinedString("Player 1", 1000, 420, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Player 2", 1200, 420, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Player 3", 1400, 420, PColor.WHITE, PColor.BLACK, 1, g);
		g.setFont(new Font("Arial", Font.BOLD, 35));
		guim.drawCenteredColoredOutlinedString("Left", 850, 480, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Right", 850, 520, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Jump", 850, 560, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Attack", 850, 600, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Pick up", 850, 640, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Drop", 850, 680, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Reload", 850, 720, PColor.WHITE, PColor.BLACK, 1, g);
		
		g.setFont(new Font("Arial", Font.BOLD, 30));
		guim.drawCenteredColoredOutlinedString("A", 1000, 480, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("D", 1000, 520, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("W", 1000, 560, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Q", 1000, 600, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("E", 1000, 640, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("G", 1000, 680, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("R", 1000, 720, PColor.WHITE, PColor.BLACK, 1, g);
		
		guim.drawCenteredColoredOutlinedString("Left Arrow", 1200, 480, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Right Arrow", 1200, 520, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Up Arrow", 1200, 560, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Control", 1200, 600, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Down Arrow", 1200, 640, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Enter", 1200, 680, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Shift", 1200, 720, PColor.WHITE, PColor.BLACK, 1, g);
		
		guim.drawCenteredColoredOutlinedString("Numpad 4", 1400, 480, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Numpad 6", 1400, 520, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Numpad 8", 1400, 560, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Numpad 7", 1400, 600, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Numpad 9", 1400, 640, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Numpad 3", 1400, 680, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Numpad 1", 1400, 720, PColor.WHITE, PColor.BLACK, 1, g);
		
		
		if(mapbuttons.size() > 5) {
			mapMenu.setMaxHeight(mapbuttons.size()*100);
		}
		
		if(mapMenuVisible) {
			mapMenu.draw(960-mapMenu.getWidth()/2, 300, g);
			mapMenu.getGraphics().setColor(PColor.BLACK);
			mapMenu.getGraphics().fillRect(0, 0, mapMenu.getMaxWidth(), mapMenu.getMaxHeight());
			mapMenu.getGraphics().setColor(PColor.GRAY);
			mapMenu.getGraphics().fillRect(5, 5, mapMenu.getMaxWidth()-10, mapMenu.getMaxHeight()-10);
			mapMenu.getGraphics().setFont(new Font("Arial", Font.BOLD, 35));
			mapMenu.getGraphics().setColor(PColor.WHITE);

			for (int i = 0; i < mapbuttons.size(); i++) {
				mapbuttons.get(i).setScrollable(mapMenu);
				mapbuttons.get(i).draw(350, 20+120*i, mapMenu.getGraphics());
			}

			g.setFont(new Font("Arial", Font.BOLD, 50));
			guim.drawCenteredColoredString("List of Maps", 960, 290, PColor.WHITE, g);
		}
	}
}
