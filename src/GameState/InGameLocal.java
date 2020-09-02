package GameState;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import Entity.ItemEntity;
import Entity.Player;
import Entity.PlayerLocal;
import Entity.Projectile;
import GUI.GuiDead;
import GUI.GuiFrame;
import GUI.GuiLocal;
import GUI.GuiMain;
import GUI.GuiPause;
import GUI.GuiScreen;
import GUI.GuiTooltip;
import HUD.HudFade;
import HUD.HudLocal;
import HUD.HudPlayer;
import Item.Item;
import Item.ItemCakePiece;
import Item.ItemPistol;
import Item.ItemShotgun;
import Objective.ObjectiveManager;
import Pascal.Game;
import Pascal.GamePanel;
import Settings.Controls;
import TileMap.TileMap;
import Util.Crash;
import Util.PColor;
import Util.PImage;
import Util.PTime;
import Zone.Zone;

public class InGameLocal extends GameState {

	public static TileMap tileMap;
	public static BufferedImage sky;
	public static Paint bg;
	public static PlayerLocal player01;
	public static PlayerLocal player02;
	public static PlayerLocal player03;
	public static ArrayList<PlayerLocal> players;
	public static ArrayList<Projectile> projectiles;
	public static String[] map;
	public static ArrayList<ItemEntity> itemsOnMap;
	
	public static int playerAmount = 2;
	private static HudLocal hudLocal;

	private GuiScreen guis = new GuiScreen();
	public static HudFade hudFade;
	
	public static ArrayList<GuiFrame> frames;
	public static GuiPause guiPause;
	
	public static final int MAPTITLE = 0;
	public static final int MAPFILE = 1;
	public static final int MAPTILESET = 2;
	public static final int MAPBACKGROUND = 3;
	public static final int MAP1X = 4;
	public static final int MAP1Y = 5;
	public static final int MAP2X = 6;
	public static final int MAP2Y = 7;
	public static final int MAP3X = 6;
	public static final int MAP3Y = 7;
	
	
	private long dm_lastItemSpawn;
	private Item[] dm_items = {
			new ItemPistol(),
			new ItemPistol(),
			new ItemPistol(),
			new ItemShotgun(),
			new ItemShotgun(),
			new ItemCakePiece()
	};
	
	public InGameLocal(GameStateManager gameStateManager) {
		this.gsm = gsm;
		init();
		hudLocal = new HudLocal();
		frames = new ArrayList<GuiFrame>();
		
		guiPause = new GuiPause();
		
		
		try {
			sky = ImageIO.read(getClass().getResourceAsStream("/textures/background/sky.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init() {
		tileMap = new TileMap(120);
		tileMap.loadMap("/levels/zone0.map");
		tileMap.loadTiles("/textures/tilesets/zone0.png");
		player01 = new PlayerLocal(tileMap, "Player 1");
		player02 = new PlayerLocal(tileMap, "Player 2");
		player03 = new PlayerLocal(tileMap, "Player 3");
		projectiles = new ArrayList<Projectile>();
		players = new ArrayList<PlayerLocal>();
		itemsOnMap = new ArrayList<ItemEntity>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		dm_lastItemSpawn = System.currentTimeMillis();
		GameStateManager.setPaused(false);
	}

	@Override
	public void update() {
		if (!GameStateManager.isPaused()) {
			for(int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).update();
				if(projectiles.get(i).shouldRemove()) {
					projectiles.remove(i);
					i--;
				}
			}
			for (int i=0;i < ItemEntity.items.size();i++) {
				ItemEntity item = ItemEntity.items.get(i);
				if(item.getTileMap() == tileMap) {
					item.update();
				}
			}
			for (int i=0;i < itemsOnMap.size();i++) {
				ItemEntity item = itemsOnMap.get(i);
				if(System.currentTimeMillis() - item.getTimeSpawned() > 30000) {
					item.remove();
				}
			}
			
			if(!player01.isDead()) {
				player01.update();
			}
			if(!player02.isDead()) {
				player02.update();
			}
			if(!player03.isDead() && playerAmount == 3) {
				player03.update();
			}
			
			if(player01.isDead() && System.currentTimeMillis() - player01.getDeathTime() > 5000) {
				player01.dead = false;
				player01.setPosition(Integer.parseInt(map[4]), Integer.parseInt(map[5]));
				player01.heal(100);
			}
			if(player02.isDead() && System.currentTimeMillis() - player02.getDeathTime() > 5000) {
				player02.dead = false;
				player02.setPosition(Integer.parseInt(map[6]), Integer.parseInt(map[7]));
				player02.heal(100);
			}
			
			if(player03.isDead() && System.currentTimeMillis() - player03.getDeathTime() > 5000) {
				player03.dead = false;
				player03.setPosition(Integer.parseInt(map[8]), Integer.parseInt(map[9]));
				player03.heal(100);
			}
			
			if(GuiLocal.selectedMode == GuiLocal.MODE_DEATHMATCH) {
				updateDeathmatch();
			}
		}
		
		if(playerAmount != 3) {
			tileMap.setPosition(
					GamePanel.WIDTH / 2 - (player01.getx()+player02.getx())/2,
					GamePanel.HEIGHT / 2 - (player01.gety()+player02.gety())/2
					);
		} else {
			tileMap.setPosition(
					GamePanel.WIDTH / 2 - (player01.getx()+player02.getx()+player03.getx())/3,
					GamePanel.HEIGHT / 2 - (player01.gety()+player02.gety()+player03.gety())/3
					);
		}
		
	}
	
	public void updateDeathmatch() {
		if(System.currentTimeMillis() - dm_lastItemSpawn > 15000) {
			spawnItem();
		}
	}
	
	public void spawnItem() {
		Random rn = new Random();
		int x = 0;
		if(player01.getx() > player02.getx()) {
			x = rn.nextInt(player01.getx() - player02.getx() + 1) + player02.getx();
		} else if(player01.getx() < player02.getx()){
			x = rn.nextInt(player02.getx() - player01.getx() + 1) + player01.getx();
		} else {
			System.out.println("01x = 02x");
		}
		double r = Math.random();
		int y = 0;
		if(r >= 5) {
			y = player02.gety() - 250;
		} else {
			y = player01.gety() - 250;
		}

		int i = rn.nextInt((dm_items.length-1) - 0 + 1) + 0;
		Item item = dm_items[i];
		ItemEntity itemSpawned = new ItemEntity(tileMap, item);
		itemSpawned.setPosition(x, y);
		itemsOnMap.add(itemSpawned);
		dm_lastItemSpawn = System.currentTimeMillis();
	}

	int time;
	@Override
	public void draw(Graphics2D g) {
		g.setPaint(bg);
		g.fillRect(0, 0, 1920, 1080);
		tileMap.draw(g);
		player01.draw(g);
		player02.draw(g);
		if(playerAmount == 3) {
			player03.draw(g);
		}
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
		for (int i=0;i < ItemEntity.items.size();i++) {
			ItemEntity item = ItemEntity.items.get(i);
			if(item.getTileMap() == tileMap) {
				item.draw(g);
			}
		}
		if(Game.settings.fog && time >= 0 && time <= 100) {
			Composite c = null;
			if(time <= 16) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			if(time > 16) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			if(time > 21) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			if(time > 26) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
			if(time > 85) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			if(time > 87) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			if(time > 90) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g.setComposite(c);
			g.setColor(PImage.getColorAt(sky, time, 1));
			g.fillRect(0, 0, 1920, 1080);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
		}

		if(GamePanel.showingHud()) {
			hudLocal.draw(g);
		}
		if(GameStateManager.isPaused()) {
			GuiPause.setState(GuiPause.STATE_LOCAL);
			guiPause.draw(g);
		}
	}
	
	public void setMap(int m) {
		GamePanel.setLoading(true);
		GamePanel.loadingStringWhat = "Changing map...";
		String[] map = GuiLocal.maps.get(m);
		this.map = map;
		tileMap = new TileMap(120);
		tileMap.loadMapFromPath("../multiplayermaps/" + map[1]);
		tileMap.loadTilesFromPath("../multiplayermaps/" + map[2]);
		if(Integer.parseInt(map[3]) >= 0 && Integer.parseInt(map[3]) <= 000) {
			if(Game.settings.bgquality == 0) {
				bg = PImage.getColorAt(sky, Integer.parseInt(map[3]), 1);
			}
			else {
				GradientPaint gradient = new GradientPaint(0, 0, PImage.getColorAt(sky, Integer.parseInt(map[3]), 0), 0, 1300, PImage.getColorAt(sky, Integer.parseInt(map[3]), 1));
				bg = gradient;
			}
		}
		else {
			bg = PColor.BLACK;
		}
		time = Integer.parseInt(map[3]);
		player01 = new PlayerLocal(tileMap, "Player 1");
		player02 = new PlayerLocal(tileMap, "Player 2");
		player03 = new PlayerLocal(tileMap, "Player 3");
		player01.setPosition(Integer.parseInt(map[4]), Integer.parseInt(map[5]));
		player02.setPosition(Integer.parseInt(map[6]), Integer.parseInt(map[7]));
		player03.setPosition(Integer.parseInt(map[8]), Integer.parseInt(map[9]));
		players = new ArrayList<PlayerLocal>();
		itemsOnMap = new ArrayList<ItemEntity>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		projectiles = new ArrayList<Projectile>();
		GamePanel.setLoading(false);
		GamePanel.loadingStringWhat = "";
	}

	@Override
	public void keyPressed(int k, char c) {
		Controls.pressLocal(k);
		Controls.pressGlobal(k);
	}

	@Override
	public void keyReleased(int k) {
		Controls.releaseLocal(k);
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		Controls.mousereleaseGlobal(m);
	}
}
