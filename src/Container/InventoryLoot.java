package Container;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GUI.GuiButton;
import GameState.InGame;
import Item.Item;
import Item.ItemCakePiece;
import Pascal.GamePanel;
import Util.PAction;
import Util.PColor;

public class InventoryLoot extends Inventory {

	private Slot slot01 = new Slot();
	private Slot slot02 = new Slot();
	private Slot slot03 = new Slot();
	private Slot slot04 = new Slot();
	private Slot slot05 = new Slot();
	private Slot slot06 = new Slot();
	private Slot slot07 = new Slot();
	private Slot slot08 = new Slot();
	private Slot slot09 = new Slot();
	private Slot slot10 = new Slot();
	private Slot slot11 = new Slot();
	private Slot slot12 = new Slot();
	
	private GuiButton buttonClose;
	private String name;
	private BufferedImage icon;
	private BufferedImage background;
	private int x;
	private int y;
	
	private boolean open;
	
	public static ArrayList<InventoryLoot> openInventories = new ArrayList<InventoryLoot>();
	
	public InventoryLoot() {
		super();
		x = 623;
		y = 400;
    	try {
    		background = ImageIO.read(getClass().getResourceAsStream("/textures/gui/frame/loot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	setName("???");
    	setIcon("info");
    	buttonClose = new GuiButton("lootClose");
    	buttonClose.setType(GuiButton.TYPE_BOXHALF);
    	buttonClose.setTitle("Close");
    	buttonClose.setUse(new PAction() {
			@Override
			public void command() {
				close();
			}
    	});

    	slots.add(slot01);
    	slots.add(slot02);
    	slots.add(slot03);
    	slots.add(slot04);
    	slots.add(slot05);
    	slots.add(slot06);
    	slots.add(slot07);
    	slots.add(slot08);
    	slots.add(slot09);
    	slots.add(slot10);
    	slots.add(slot11);
    	slots.add(slot12);
	}

	@Override
	public void draw(Graphics2D g) {
		for (int i=0;i < GamePanel.buttons.size();i++) {
			if(GamePanel.buttons.get(i).isHovered()) {
				System.out.println(GamePanel.buttons.get(i).getID());
			}
		}
		g.drawImage(background, x, y, null);
		buttonClose.draw(x + 545, y+340, g);
		slot01.draw(x + 52, y+120, g);
		slot02.draw(x + 150, y+120, g);
		slot03.draw(x + 248, y+120, g);
		slot04.draw(x + 346, y+120, g);
		slot05.draw(x + 444, y+120, g);
		slot06.draw(x + 542, y+120, g);
		slot07.draw(x + 52, y+218, g);
		slot08.draw(x + 150, y+218, g);
		slot09.draw(x + 248, y+218, g);
		slot10.draw(x + 346, y+218, g);
		slot11.draw(x + 444, y+218, g);
		slot12.draw(x + 542, y+218, g);
		
		if(name != null) {
			g.setFont(new Font("Arial", Font.BOLD, 40));
			guimaster.drawColoredOutlinedString(name, x + 80, y + 50, PColor.WHITE, PColor.BLACK, 1, g);
		}
		if(icon != null) {
			g.drawImage(icon, x + 7, y + 6, 62, 62, null);
		}
	}
	public void setName(String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
	public void setIcon(String i) {
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/faces/" + i + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean isOpen() {
		return open;
	}	
	public void addItem(Item item) {
		super.addItem(item);
		loopCheck:
		for (int i=0;i < slots.size();i++) {
			if(slots.get(i).isEmpty()) {
				slots.get(i).setItem(item);
				break loopCheck;
			}
		}
	}
	public void click() {
		for (int i=0;i < slots.size();i++) {
			if(slots.get(i).isHovered()) {
				InGame.player.itemAdd(slots.get(i).getItem());
				slots.get(i).removeItem();
			}
		}
	}
	public void open() {
		for (int i=0;i < InventoryLoot.openInventories.size();i++) {
			if(InventoryLoot.openInventories.get(i).isOpen()) {
				InventoryLoot.openInventories.get(i).close();
			}
		}
		open = true;
		if(!openInventories.contains(this)) {
			openInventories.add(this);
		}
	}
	public void close() {
		open = false;
		if(openInventories.contains(this)) {
			openInventories.remove(this);
		}
	}
}
