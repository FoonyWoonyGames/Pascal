package Item;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Container.Slot;
import Entity.ItemEntity;
import Pascal.Game;
import TileMap.TileMap;
import Util.ItemRegistry;
import Util.PColor;
import Util.Sound;

public abstract class Item {
	protected long droptime = 0;
	public int id = 0;
	public String nameUnlocalized = "none";
	public String nameLocalized;
	protected long TimeOfPickup;
	protected boolean dropped = false;
	protected String sound = "gui.select";
	protected Sound soundPlayer;
	protected String idText = "0";
	protected String variation = "01";
	protected BufferedImage image;
	protected Slot slot;
	protected double weight;
	protected int price;
	protected boolean inInventory;
	protected long cooldown = 0;
	protected long lastUse = 0;
	
	protected int rank;
	protected boolean validKey = false;
	protected boolean validWeapon = false;
	protected boolean validFood = false;
	protected boolean global = false;
	protected String description;
	protected boolean sellable = false;

	public static final int USELESS = 0;
	public static final int NORMAL = 1;
	public static final int GOOD = 2;
	public static final int LEGENDARY = 3;
	
	public static final int AIR = 0;
	public static final int HP = 1;
	public static final int KEY = 2;
	public static final int PISTOL = 3;
	
	public Item() {
		soundPlayer = new Sound();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String str) {
		description = str;
	}
	public boolean isGlobal() {
		return global;
	}
	public int getRank() {
		return rank;
	}
	public Color getRankColor() {
		Color c = null;
		if(rank == 1) {
			c = PColor.ITEM_NORMAL;
		}
		else if(rank == 2) {
			c = PColor.ITEM_GOOD;
		}
		else if(rank == 3) {
			c = PColor.ITEM_LEGENDARY;
		}
		else {
			c = PColor.ITEM_USELESS;
		}
		return c;
	}
	public double getWeight() {
		return weight;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String path) {
		sound = path;
	}
	public void playSound() {
		soundPlayer.play(sound);
		soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
	}
	public void playSound(String s) {
		soundPlayer.play(s);
		soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
	}
	public void drop(int drx, int dry, TileMap tm) {
		Game.itemRegistry = new ItemRegistry();
		ItemEntity item = new ItemEntity(tm, this);
		item.setPosition(drx, dry);
	}
	public BufferedImage getSprite() {
		return image;
	}
	public int getID() {
		return this.id;
	}
	public String getTextID() {
		return this.idText;
	}
	public void setUnlocalizedName(String str) {
		nameUnlocalized = str;
	}
	public String getUnlocalizedName() {
		String name;
		if(nameLocalized == null) {
			name = nameUnlocalized;
		}
		else {
			name = nameLocalized;
		}
		return name;
	}
	public void setLocalizedName(String str) {
		nameLocalized = str;
	}
	public String getLocalizedName() {
		return nameLocalized;
	}
	public boolean isKey() {
		return validKey;
	}
	public boolean isWeapon() {
		return validWeapon;
	}
	public boolean inInventory() {
		return inInventory;
	}
	public void setInInventory(boolean b) {
		inInventory = b;
	}
	public void setVariation(String var) {
		variation = var;
	}
	public long getCooldown() {
		return cooldown;
	}
	public boolean onCooldown() {
		return System.currentTimeMillis() - getLastUse() < getCooldown();
	}
	public long getLastUse() {
		return lastUse;
	}
	public void lastUse() {
		lastUse = System.currentTimeMillis();
	}
	public boolean isValidKey() {
		return validKey;
	}
	public boolean isValidWeapon() {
		return validWeapon;
	}
	public boolean isValidFood() {
		return validFood;
	}
	public void setPrice(int p) {
		price = 0;
	}
	public int getPrice() {
		return price;
	}
	public void setSlot(Slot s) {
		slot = s;
	}
	public Slot getSlot() {
		return slot;
	}
	
	public abstract void onPickup();
	public abstract void onUse();
	public abstract void onEquip();
	public abstract void onHold();
}
