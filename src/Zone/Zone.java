package Zone;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Entity.MapObject;
import GUI.GuiMaster;
import GameState.InGame;
import Pascal.Game;
import TileMap.Background;
import TileMap.TileMap;
import TileMap.Water;
import Util.PColor;
import Util.PImage;
import Util.PInteger;
import Util.PTime;
import Util.Sound;

public class Zone {
	protected String nameLocalized;
	protected String nameUnlocalized;
	protected String nameSub = "";
	protected int id;
	protected TileMap tileMap;
	protected Background bg;
	protected BufferedImage sky;
	protected boolean interior;
	protected String textureMap;
	protected String pathAmbient;
	protected String pathMusic;
	public ArrayList<MapObject> objects;
	public ArrayList<Water> waters;
	public ArrayList<String> wallsInvisible;
	
	protected Point startingPoint;
	
	protected Sound soundAmbient;
	protected Sound soundMusic;
	
	public static final int ZONE0 = 0;
	public static final int EVIGILANTHILLS01 = 1;
	public static final int EVIGILANTHILLS02 = 2;
	public static final int EVIGILANTHILLS03 = 3;
	public static final int HEIMDURNK01 = 4;
	public static final int HEIMDURNK02 = 5;

	static long splashTime;
	static GuiMaster guim = new GuiMaster();
	
	public static Zone[] Zones = {
			new Zone(),
			new EvigilantHills01(),
			new EvigilantHills02(),
			new EvigilantHills03(),
			new Heimdurnk01(),
			new Heimdurnk02(),
			new EvigilantCity01()
	};
	public Zone() {
		objects = new ArrayList<MapObject>();
		waters = new ArrayList<Water>();
		wallsInvisible = new ArrayList<String>();
		soundAmbient = new Sound();
		soundMusic = new Sound();
		
		id = 0;
		nameUnlocalized = "zone0";
		tileMap = new TileMap(60);
		textureMap = "zone0.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		tileMap.loadTiles("/textures/tilesets/zone0.png");
		interior = false;
		try {
			sky = ImageIO.read(getClass().getResourceAsStream("/textures/background/sky.png"));
			if(PTime.getMonth() == PTime.MONTH_DECEMBER) {
				sky = ImageIO.read(getClass().getResourceAsStream("/textures/background/snow/sky.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		pathAmbient = "ambient.ambientNature01";
		
		startingPoint = new Point(400, 4480);
		
		loadTiles();
	}
	public void init() {
		if(pathAmbient != null) {
			soundAmbient.play(pathAmbient);
			soundAmbient.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundAmbient))));
		}
	}
	public void outit() {
		soundAmbient.stop();
	}
	public void loadTiles() {
		tileMap.loadTiles("/textures/tilesets/" + textureMap);
		if(PTime.getMonth() == PTime.MONTH_DECEMBER) {
			tileMap.loadTiles("/textures/tilesets/snow/" + textureMap);
		}
	}
	public void leave() { }
	public void update() { 
		for (int i=0;i < waters.size();i++) {
			waters.get(i).update();
		}
		for (int i=0;i < objects.size();i++) {
			objects.get(i).update();
		}
		if(pathAmbient != null && !pathAmbient.isEmpty() && soundAmbient.isPaused()) {
			soundAmbient.resume();
		}
		soundAmbient.loop();
	}
	public void draw(Graphics2D g) {
		int time = PInteger.convertTimeProcent();
		GradientPaint gradient = new GradientPaint(0, 0, PImage.getColorAt(sky, time, 0), 0, 1300, PImage.getColorAt(sky, time, 1));
		if(!interior) {
			if(Game.settings.bgquality == 0) {
				g.setColor(PImage.getColorAt(sky, time, 1));
			}
			else {
				g.setPaint(gradient);
			}
		}
		else {
			g.setColor(PColor.BLACK);
		}
		BufferedImage s = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
		Graphics2D sg = (Graphics2D) s.getGraphics();
		if(!interior) {
			if(Game.settings.bgquality == 0) {
				sg.setColor(PImage.getColorAt(sky, time, 1));
			}
			else {
				sg.setPaint(gradient);
			}
		}
		else {
			sg.setColor(PColor.BLACK);
		}
		sg.fillRect(0, 0, 1920, 1080);
		g.drawImage(s, 0, 0, null);
		tileMap.draw(g);
		for (int i=0;i < objects.size();i++) {
			objects.get(i).draw(g);
		}
		InGame.player.draw(g);
		if(Game.settings.fog && !interior) {
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
	}
	public Sound getAmbient() {
		return soundAmbient;
	}
	public Sound getMusic() {
		return soundMusic;
	}
	public Point getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(int x, int y) {
		startingPoint = new Point(x, y);
	}
	public int getID() {
		return id;
	}
	public String getUnlocalizedName() {
		return nameUnlocalized;
	}
	public String getLocalizedName() {
		return nameLocalized;
	}
	public String getSubName() {
		return nameSub;
	}
	public void setUnlocalizedName(String str) {
		nameUnlocalized = str;
	}
	public void setLocalizedName(String str) {
		nameLocalized = str;
	}
	public void setSubName(String str) {
		nameSub = str;
	}
	public TileMap getTilemap() {
		return tileMap;
	}
	public void setTilemap(TileMap tm) {
		tileMap = tm;
	}
	public void setBackground(String str) {
		bg = new Background("/textures/background/" + Game.settings.bgquality + "/" + str + ".png", 0.1);
	}
	static boolean splashDrawn;
	static int y;
	public static void drawSplash(Zone z,Graphics2D g) {
		if(!splashDrawn) {
			splashDrawn = true;
			splashTime = System.currentTimeMillis();
			y = 400;
		}
		long timePassed = System.currentTimeMillis() - splashTime;
		Composite c = null;
		boolean changeY = false;
		if(timePassed < 10) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
			g.setComposite(c);
		}
		if(timePassed > 10) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			g.setComposite(c);
		}
		if(timePassed > 50) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g.setComposite(c);
		}
		if(timePassed > 100) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g.setComposite(c);
		}
		if(timePassed > 150) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g.setComposite(c);
		}
		if(timePassed > 200) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g.setComposite(c);
		}
		if(timePassed > 250) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g.setComposite(c);
		}
		if(timePassed > 300) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			g.setComposite(c);
		}
		if(timePassed > 350) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			g.setComposite(c);
		}
		if(timePassed > 400) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			g.setComposite(c);
		}
		if(timePassed > 500) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
		}
		
		
		
		if(timePassed > 1500) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			g.setComposite(c);
			changeY=true;
		}
		if(timePassed > 1550) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			g.setComposite(c);
		}
		if(timePassed > 1600) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			g.setComposite(c);
		}
		if(timePassed > 1650) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g.setComposite(c);
		}
		if(timePassed > 1700) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g.setComposite(c);
		}
		if(timePassed > 1750) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g.setComposite(c);
			changeY=true;
		}
		if(timePassed > 1800) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g.setComposite(c);
		}
		if(timePassed > 1850) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g.setComposite(c);
		}
		if(timePassed > 1900) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			g.setComposite(c);
		}
		if(timePassed > 1950) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
			g.setComposite(c);
		}
		if(changeY) {
			y--;
		}
		g.setFont(new Font("Arial", Font.BOLD, 60));
		String name;
		String sub;
		if(z.getLocalizedName() != null) {
			name = z.getLocalizedName();
		}
		else {
			name = z.getUnlocalizedName();
		}
		sub = z.getSubName();
		guim.drawCenteredColoredOutlinedString(name.toUpperCase(), 960, y, PColor.LIGHT_YELLOW, PColor.BLACK, 1, g);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		if(!sub.isEmpty()) {
			guim.drawCenteredColoredOutlinedString("(" + sub.toUpperCase() + ")", 960, y+40, PColor.YELLOW, PColor.BLACK, 1, g);
		}
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(c);
		if(timePassed > 2000) {
			splashDrawn = false;
		}
	}
	public void drawOver(Graphics2D g) { }
	public void addWater(Water w) {
		waters.add(w);
	}
	public void addObject(MapObject mo) {
		objects.add(mo);
	}
	public void removeObject(MapObject mo) {
		objects.remove(mo);
	}
	public void addWall(int x) {
		String w = "INVSW=" + x;
		wallsInvisible.add(w);
	}
	public void removeWall(int w) {
		for (int i=0;i < waters.size();i++) {
			if(i == w) {
				wallsInvisible.remove(i);
			}
		}
	}
}
