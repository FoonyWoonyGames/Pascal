package Entity;

import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GUI.GuiMaster;
import GameState.InGame;
import TileMap.TileMap;
import Util.PColor;


public class Door extends MapObject {

	public static ArrayList<Door> Doors = new ArrayList<Door>(); 
	
	private BufferedImage[] sprites;
	private ActionListener action;
	private int variation = 01;
	private String nameUnlocalized = "LAND OF NOWHERE";
	private String nameLocalized;
	private BufferedImage icon;
	private boolean locked;
	private String doorId;
	
	public Door(TileMap tm) {
		super(tm);
		
		width = 120;
		height = 240;
		cwidth = 120;
		cheight = 240;
		facingRight=true;
		
		doorId = "00";
		Doors.add(this);

		if(nameLocalized == null) {
			nameLocalized = nameUnlocalized;
		}
		
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/door01.png"));
			
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = icon.getSubimage(i * width, 0, width, height);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getID() {
		return doorId;
	}
	public void setID(String str) {
		doorId = str;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean b) {
		locked = b;
	}
	public void use() {
		if(!isLocked()) {
			InGame.hudFade.fade(PColor.BLACK, 1.5);
			try {
				Thread.sleep(450/1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		action.actionPerformed(null);
	}
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		if(!invisible) {
			super.draw(g);
		}
	}
	public void setUse(ActionListener a) {
		action = a;
	}
	public void setUnlocalizedName(String str) {
		nameUnlocalized = str;
	}
	public String getUnlocalizedName() {
		return nameUnlocalized;
	}
	public void setLocalizedName(String str) {
		nameLocalized = str;
	}
	public String getLocalizedName() {
		return nameLocalized;
	}
	public void setVariation(int var) {
		variation = var;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/door" + variation + ".png"));
			
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = icon.getSubimage(i * width, 0, width, height);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
