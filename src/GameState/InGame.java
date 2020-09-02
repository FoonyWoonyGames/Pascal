package GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Container.InventoryLoot;
import Entity.Explosion;
import Entity.ItemEntity;
import Entity.Player;
import GUI.GuiDead;
import GUI.GuiFrame;
import GUI.GuiLoadingSavefile;
import GUI.GuiLoadingZone;
import GUI.GuiPause;
import GUI.GuiScreen;
import GUI.GuiTooltip;
import GUI.GuiVendor;
import HUD.HudDialogue;
import HUD.HudFade;
import HUD.HudPlayer;
import HUD.HudSplash;
import Item.Item;
import Item.ItemSunblade;
import Objective.ObjectiveManager;
import Pascal.Game;
import Pascal.GamePanel;
import Settings.Controls;
import Storage.SaveManager;
import TileMap.*;
import Util.PColor;
import Zone.EvigilantHills01;
import Zone.Zone;


public class InGame extends GameState {

	public static TileMap tileMap;

	public static int state;
	public static Player player;
	public static String cheat;

	private GuiScreen guis = new GuiScreen();
	private HudPlayer hudPlayer;
	public static HudFade hudFade;
	
	public static ArrayList<String> completedObjectives;
	public static ObjectiveManager objm;
	public static int cash;
	private static Zone zone;
	private static int currentZone;
	private static long timeofZoneChange;
	public static ArrayList<GuiFrame> frames;
	public static long lastAutoSave;
	
	public static boolean holdsItem;
	public static Item heldItem;
	public static GuiTooltip tooltip;
	
	public static GuiDead guiDead;
	public static GuiPause guiPause;
	public static GuiLoadingSavefile guiLoadingSavefile;
	public static GuiLoadingZone guiLoadingZone;
	
	private static boolean loadingSavefile = false;
	private static String currentSavefile = "save1";

	private static boolean loadingZone = false;


	public InGame(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		frames = new ArrayList<GuiFrame>();
		
		guiDead = new GuiDead();
		guiPause = new GuiPause();
		guiLoadingSavefile = new GuiLoadingSavefile();
		guiLoadingZone = new GuiLoadingZone();
	}
	
	
	public void init() {
		guiLoadingZone = new GuiLoadingZone();
		loadingZone = true;
		guiLoadingZone.setName("Evigilant Hills");
		guiLoadingZone.setState(0);
		objm = new ObjectiveManager();
		completedObjectives = new ArrayList<String>();
		GameStateManager.setDeath(false);
		GameStateManager.setPaused(false);
		for (int i=0;i < HudDialogue.Dialogues.size();i++) {
			HudDialogue.Dialogues.get(i).setShowing(false);
		}
		lastAutoSave = System.currentTimeMillis();
		guiLoadingZone.setState(1);
		if(zone != null) {
			zone.outit();
		}
		zone = new EvigilantHills01();
		currentZone = 1;
		zone.init();
		zone.getAmbient().pause();
		tileMap = zone.getTilemap();
		guiLoadingZone.setState(2);
		player = new Player(tileMap);
		GamePanel.currentPlayer = player;
		player.setPosition(zone.getStartingPoint().x, zone.getStartingPoint().y);
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
		hudPlayer = new HudPlayer(player);
		hudFade = new HudFade();
		ItemSunblade.checkFor();
		guiLoadingZone.setState(3);
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loadingZone = false;
		GameStateManager.setPaused(false);
	}
		public void update() {
			if (!GameStateManager.isPaused() && !player.isDead()) {
				zone.update();
				player.update();
				for (int i=0;i < GamePanel.buttons.size();i++) {
					GamePanel.buttons.get(i).setHovered(false);
				}

				for (int i=0;i < ItemEntity.items.size();i++) {
					ItemEntity item = ItemEntity.items.get(i);
					if(item.getTileMap() == tileMap) {
						item.update();
					}
				}
				if(!tileMap.isFrozen()) {
					tileMap.setPosition(
							GamePanel.WIDTH / 2 - player.getx(),
							GamePanel.HEIGHT / 2 - player.gety()
							);
				}
				
				if(Game.settings.autosave) {
					if(System.currentTimeMillis() - lastAutoSave > Game.settings.autosavetime) {
						lastAutoSave = System.currentTimeMillis();
						SaveManager.Save(player, 1);
						new HudSplash("Autosaving", PColor.TIP);
					}
				}
			}
			for (int i=0;i < frames.size();i++) {
				frames.get(i).update();
			}
			if(GuiVendor.currentVendor != null) {
				GuiVendor.currentVendor.update();
			}
			
	        
		}
		public void draw(Graphics2D g) {
			zone.draw(g);
			for (int i=0;i < ItemEntity.items.size();i++) {
				ItemEntity item = ItemEntity.items.get(i);
				if(item.getTileMap() == tileMap && item != null) {
					item.draw(g);
				}
			}

			if(GamePanel.showingHud()) {
				hudPlayer.draw(g);
				for (int i=0;i < HudDialogue.Dialogues.size();i++) {
					HudDialogue.Dialogues.get(i).draw(g);
				}
				if(GuiVendor.currentVendor != null) {
					GuiVendor.currentVendor.draw(g);
				}
				for (int i=0;i < InventoryLoot.openInventories.size();i++) {
					if(InventoryLoot.openInventories.get(i).isOpen()) {
						InventoryLoot.openInventories.get(i).draw(g);
					}
				}
				if(System.currentTimeMillis() - timeofZoneChange <= 2000 && GamePanel.showingHud()) {
					Zone.drawSplash(zone, g);
				}
				for (int i=0;i < HudSplash.Splashes.size();i++) {
					HudSplash.Splashes.get(i).draw(g);
				}
				if(holdsItem) {
					g.drawImage(heldItem.getSprite().getScaledInstance(70, 70, BufferedImage.SCALE_FAST),
							(int) GamePanel.mouseX-26, (int) GamePanel.mouseY-26, null);
				}
				objm.draw(g);
				guis.drawLocation(player, g);
				zone.drawOver(g);
				for(int i = 0; i < Explosion.Explosions.size(); i++) {
					Explosion.Explosions.get(i).draw(g);
				}
				for (int i=0;i < GuiTooltip.TTips.size();i++) {
					GuiTooltip.TTips.get(i).draw(g);
				}
			}
			if(player.isDead()) {
				guiDead.draw(g);
			}
			hudFade.draw(g);
			if(GameStateManager.isPaused()) {
				guiPause.draw(g);
			}
			for (int i=0;i < frames.size();i++) {
				frames.get(i).draw(g);
			}
			
			if(loadingSavefile) {
				guiLoadingSavefile.draw(g);
			} else if(loadingZone) {
				guiLoadingZone.draw(g);
			}
		}
		
		public void keyPressed(int k, char c) {
			Controls.pressStandard(player, k);
			Controls.pressGlobal(k);
		}		
		public void keyReleased(int k) {
			Controls.releaseStandard(player, k);
		}

		public void keyTyped(int k) {
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
			Controls.mousereleaseGlobal(e);
		}

		public static void setZone(int z) {
			loadingZone = true;
			guiLoadingZone.setName(Zone.Zones[z].getLocalizedName());
			guiLoadingZone.setState(0);
			zone.getAmbient().stop();
			timeofZoneChange = System.currentTimeMillis();
 			zone = Zone.Zones[z];
			currentZone = z;
			tileMap = zone.getTilemap();
			guiLoadingZone.setState(1);
			player.setTileMap(tileMap);
			player.setPosition(zone.getStartingPoint().x, zone.getStartingPoint().y);
			tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
			guiLoadingZone.setState(2);
			zone.init();
			guiLoadingZone.setState(3);
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			loadingZone = false;
		}
		public static int getZoneID() {
			return currentZone;
		}
		public static Zone getZone() {
			return zone;
		}
		public static void displayFrame(GuiFrame fr) {
			frames.add(fr);
		}
		public static void drawZoneName() {
			timeofZoneChange = System.currentTimeMillis();
		}

		public static void loadSavefile(boolean b, String name) {
			if(b) {
				loadingSavefile = true;
				currentSavefile = name;
			} else {
				loadingSavefile = false;
			}
		}

}
		