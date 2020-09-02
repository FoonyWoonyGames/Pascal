package Customization;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Account.User;
import Pascal.Game;

public class ClothingItem {
	protected String name;
	protected BufferedImage sprite;
	protected String path;
	protected String id;
	protected boolean inDatabase;
	protected boolean locked;
	protected int type;
	protected String category;
	public ClothingItem(String n, String s, String i, boolean idb, boolean l, int t, String c) {
		name = n;
		path = s;
//		setSprite(path);
		id = i;
		inDatabase = idb;
		locked = l;
		type = t;
		category = c;
		
		if(type == ClothingRegistry.TYPE_HAT) {
			Game.clothingRegistry.hats.add(this);
		}
		else if(type == ClothingRegistry.TYPE_OVERCOAT) {
			Game.clothingRegistry.overcoats.add(this);
		}
		else if(type == ClothingRegistry.TYPE_TOP) {
			Game.clothingRegistry.tops.add(this);
		}
		else if(type == ClothingRegistry.TYPE_LEGS) {
			Game.clothingRegistry.legs.add(this);
		}
		else if(type == ClothingRegistry.TYPE_FEET) {
			Game.clothingRegistry.feet.add(this);
		}
		else if(type == ClothingRegistry.TYPE_GLASSES) {
			Game.clothingRegistry.glasses.add(this);
		}
		else if(type == ClothingRegistry.TYPE_MASK) {
			Game.clothingRegistry.masks.add(this);
		}
		else if(type == ClothingRegistry.TYPE_SET) {
			Game.clothingRegistry.sets.add(this);
		}
		
		if(!category.equalsIgnoreCase("null")) {
			Game.clothingRegistry.getCategory(type, category).content().add(this);
		}
		
		checkDatabase();
	}
	public void checkDatabase() {
		if(inDatabase()) {
			if(User.hasUnlocked(id, Game.username)) {
				setLocked(false);
			}
		}
	}
	public int getType() {
		return type;
	}
	public String getCategory() {
		return category;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean b) {
		locked = b;
	}
	public boolean inDatabase() {
		return inDatabase;
	}
	public String getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public BufferedImage getSprite() {
		setSprite(path);
		return sprite;
	}
	public void setSprite(String str) {
		if(str.equalsIgnoreCase("null")) {
			sprite = null;
		}
		else {
			String path = str.replace(".", "/");
			try {
				sprite = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/clothing/" + path + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
