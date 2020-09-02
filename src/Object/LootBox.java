package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Container.InventoryLoot;
import Entity.MapObject;
import GUI.GuiMaster;
import HUD.HudDialogueSign;
import Item.Item;
import TileMap.TileMap;
import Util.PAction;

public class LootBox extends MapObject {

	public static ArrayList<LootBox> Loots = new ArrayList<LootBox>();
	
	private String variation;
	private BufferedImage icon;
	private String name;
	private InventoryLoot inventory;
	public LootBox(TileMap tm) {
		super(tm);

		fallSpeed = 0.6;
		maxFallSpeed = 24.0;
		variation = "01";
		name = "???";
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/loot" + variation + ".png"));
			cwidth = icon.getWidth();
			cheight = icon.getHeight();
			width = icon.getWidth();
			height = icon.getHeight();
		} catch(Exception e) {
			e.printStackTrace();
		}
		inventory = new InventoryLoot();
		
		Loots.add(this);
	}

	private void getNextPosition() {
		if(falling) {
			dy += fallSpeed;
		}
	}
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		getNextPosition();
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		int locx = (int)(x + xmap - width / 2);
		int locy = (int)(y + ymap - height / 2);
		g.drawImage(icon, locx, locy, null);
	}
	public void open() {
		inventory.open();
	}
	public void addItem(Item i) {
		inventory.addItem(i);
	}
	public void removeItem(Item i) {
		inventory.removeItem(i);
	}
	public void setVariation(String v) {
		variation = v;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/sign" + variation + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setName(String n) {
		name = n;
		inventory.setName(n);
	}
	public void setIcon(String n) {
		inventory.setIcon(n);
	}
	public String getName() {
		return name;
	}
}
