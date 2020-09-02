package Container;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.InGame;
import Item.Item;
import Util.PColor;

public class InventoryPlayer extends Inventory {
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
	private Slot slot13 = new Slot();
	private Slot slot14 = new Slot();
	private Slot slot15 = new Slot();
	private Slot slot16 = new Slot();
	private Slot slot17 = new Slot();
	private Slot slot18 = new Slot();
	private Slot slot19 = new Slot();
	private Slot slot20 = new Slot();
	private Slot slot21 = new Slot();
	private Slot slot22 = new Slot();
	private Slot slot23 = new Slot();
	private Slot slot24 = new Slot();
	private Slot slot25 = new Slot();
	private Slot slot26 = new Slot();
	private Slot slot27 = new Slot();
	private Slot slot28 = new Slot();
	private Slot slot29 = new Slot();
	private Slot slot30 = new Slot();
	private Slot slotHand01 = new Slot();
	private Slot slotHand02 = new Slot();
	private Slot slotHand03 = new Slot();
	private Slot slotHand04 = new Slot();
	private Slot slotHand05 = new Slot();
	private boolean open;
	private BufferedImage background;
	private BufferedImage handslotBackground;
	private BufferedImage handslotSelected;
	private int selectedHand;
	
	private int x;
	private int y;
	public InventoryPlayer() {
		super();
		x = 460;
		y = 200;
		maxItems = 35;
    	try {
    		background = ImageIO.read(getClass().getResourceAsStream("/textures/gui/frame/inventory_player.png"));
    		handslotBackground = ImageIO.read(getClass().getResourceAsStream("/textures/gui/inventory/handslot.png"));
    		handslotSelected = ImageIO.read(getClass().getResourceAsStream("/textures/gui/inventory/handslotSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

    	slots.add(slotHand01);
    	slots.add(slotHand02);
    	slots.add(slotHand03);
    	slots.add(slotHand04);
    	slots.add(slotHand05);
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
    	slots.add(slot13);
    	slots.add(slot14);
    	slots.add(slot15);
    	slots.add(slot16);
    	slots.add(slot17);
    	slots.add(slot18);
    	slots.add(slot19);
    	slots.add(slot20);
    	slots.add(slot21);
    	slots.add(slot22);
    	slots.add(slot23);
    	slots.add(slot24);
    	slots.add(slot25);
    	slots.add(slot26);
    	slots.add(slot27);
    	slots.add(slot28);
    	slots.add(slot29);
    	slots.add(slot30);
	}
	public void draw(Graphics2D g) {
		g.drawImage(handslotSelected, 10, 196 + selectedHand*100, null);
		g.drawImage(handslotBackground, 0, 160, null);
		slotHand01.draw(20, 206, g);
		slotHand02.draw(20, 306, g);
		slotHand03.draw(20, 406, g);
		slotHand04.draw(20, 506, g);
		slotHand05.draw(20, 606, g);
		if(isOpen()) {
			g.drawImage(background, null, x, y);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			guimaster.drawColoredOutlinedString("Bag", x+120, y+52, PColor.WHITE, PColor.BLACK, 1, g);
			slot01.draw(x+32, y+146, g);
			slot02.draw(x+132, y+146, g);
			slot03.draw(x+232, y+146, g);
			slot04.draw(x+332, y+146, g);
			slot05.draw(x+432, y+146, g);
			slot06.draw(x+532, y+146, g);
			
			slot07.draw(x+32, y+246, g);
			slot08.draw(x+132, y+246, g);
			slot09.draw(x+232, y+246, g);
			slot10.draw(x+332, y+246, g);
			slot11.draw(x+432, y+246, g);
			slot12.draw(x+532, y+246, g);
			
			slot13.draw(x+32, y+346, g);
			slot14.draw(x+132, y+346, g);
			slot15.draw(x+232, y+346, g);
			slot16.draw(x+332, y+346, g);
			slot17.draw(x+432, y+346, g);
			slot18.draw(x+532, y+346, g);
			
			slot19.draw(x+32, y+446, g);
			slot20.draw(x+132, y+446, g);
			slot21.draw(x+232, y+446, g);
			slot22.draw(x+332, y+446, g);
			slot23.draw(x+432, y+446, g);
			slot24.draw(x+532, y+446, g);
			
			slot25.draw(x+32, y+546, g);
			slot26.draw(x+132, y+546, g);
			slot27.draw(x+232, y+546, g);
			slot28.draw(x+332, y+546, g);
			slot29.draw(x+432, y+546, g);
			slot30.draw(x+532, y+546, g);
			
			slotHand01.draw(x+764, y+146, g);
			slotHand02.draw(x+764, y+246, g);
			slotHand03.draw(x+764, y+346, g);
			slotHand04.draw(x+764, y+446, g);
			slotHand05.draw(x+764, y+546, g);
			
			g.setFont(new Font("Arial", Font.BOLD, 30));
			guimaster.drawColoredOutlinedString(InGame.cash + "$", x + 30, y + 660, PColor.DARK_GREEN, PColor.BLACK, 1, g);
		}
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
	public void removeItem(Item item) {
		super.removeItem(item);
		loopCheck:
		for (int i=0;i < slots.size();i++) {
			if(slots.get(i).getItem() == item) {
				slots.get(i).removeItem();
				break loopCheck;
			}
		}
	}
	public void setSelectedHand(int h) {
		selectedHand = h;
	}
	public int getHand() {
		return selectedHand;
	}
	public Item getItemFromHand() {
		Item i = null;
		if(selectedHand == 0) {
			i = slotHand01.getItem();
		}
		else if(selectedHand == 1) {
			i = slotHand02.getItem();
		}
		else if(selectedHand == 2) {
			i = slotHand03.getItem();
		}
		else if(selectedHand == 3) {
			i = slotHand04.getItem();
		}
		else if(selectedHand == 4) {
			i = slotHand05.getItem();
		}
		else {
			i = slotHand01.getItem();
		}
		return i;
	}
}
