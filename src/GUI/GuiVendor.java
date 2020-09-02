package GUI;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Npc;
import GameState.InGame;
import Item.Item;
import Pascal.Game;
import Pascal.GamePanel;
import Util.ItemRegistry;
import Util.PAction;
import Util.PColor;

public class GuiVendor extends GuiMaster {
	public static GuiVendor currentVendor;
	
	private ArrayList<Item> storage;
	private ArrayList<GuiVendorSlot> slots;
	private Npc vendor;
	private boolean visible;
	private Image face;
	private String title = "No vendortitle (Please set one).";
	
	private int x = 320;
	private int y = 160;
	private int page;
	
	private BufferedImage frame;
	private GuiButton buttonClose;
	private GuiButton buttonNext;
	private GuiButton buttonPrev;
	public ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	public GuiVendor() {
		currentVendor = this;
		storage = new ArrayList<Item>();
		slots = new ArrayList<GuiVendorSlot>();
    	try {
    		frame = ImageIO.read(getClass().getResourceAsStream("/textures/gui/frame/vendor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	buttonClose = new GuiButton("vendorClose");
    	buttonNext = new GuiButton("vendorNext");
    	buttonPrev = new GuiButton("vendorPrev");
    	
    	buttons.add(buttonClose);
    	buttons.add(buttonNext);
    	buttons.add(buttonPrev);
    	
		buttonClose.setType(GuiButton.TYPE_CROSS);
		buttonClose.setTitle("");
		buttonClose.setUse(new PAction() {
			public void command() {
				dispose();
			}
		});
		
		buttonNext.setType(GuiButton.TYPE_ARROWRIGHT);
		buttonNext.setTitle("");
		buttonNext.setUse(new PAction() {
			public void command() {
				page++;
			}
		});

		buttonPrev.setType(GuiButton.TYPE_ARROWLEFT);
		buttonPrev.setTitle("");
		buttonPrev.setUse(new PAction() {
			public void command() {
				page--;
			}
		});
	}
	public void update() {
		buttonClose.update();
		buttonNext.update();
		buttonPrev.update();
	}
	public void draw(Graphics2D g) {
		if(visible) {
			GamePanel.currentPlayer.setDown(false);
			GamePanel.currentPlayer.setRight(false);
			GamePanel.currentPlayer.setLeft(false);
			GamePanel.currentPlayer.setUp(false);
			g.drawImage(frame, null, x, y);
			if(vendor != null) {
				g.drawImage(face, x+70, y+4, null);
				drawColoredOutlinedString(vendor.getLongName(), x+146, y+52, PColor.WHITE, PColor.BLACK, 1, g);
			}
			drawColoredOutlinedString(title, x+40, y+120, PColor.INTERFACE, PColor.BLACK, 1, g);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.setColor(PColor.WHITE);
			g.drawString("Page", 1330, y+818);
			int p = page + 1;
			int mp = 1;
			if (slots.size() > 15) {
				mp = 2;
			}
			if (slots.size() > 30) {
				mp = 3;
			}
			if (slots.size() > 45) {
				mp = 4;
			}
			if (slots.size() > 60) {
				mp = 5;
			}
			if(page == 0) {
				buttonPrev.setDisabled(true);
			}
			else {
				buttonPrev.setDisabled(false);
			}
			if(page == 3 || mp == 1 || page == mp - 1) {
				buttonNext.setDisabled(true);
			}
			else {
				buttonNext.setDisabled(false);
			}
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString(p + "/" + mp, 1342, y+846);
			drawBackwardsColoredOutlinedString(InGame.cash + "$", 1510, 220, PColor.DARK_GREEN, PColor.BLACK, 1, g);
			for (int i=0;i < slots.size();i++) {
				if(page == 0) {
					if(i == 0) { slots.get(i).draw(320, 302, g); }
					if(i == 1) { slots.get(i).draw(320, 448, g); }
					if(i == 2) { slots.get(i).draw(320, 594, g); }
					if(i == 3) { slots.get(i).draw(320, 740, g); }
					if(i == 4) { slots.get(i).draw(320, 864, g); }
					
					if(i == 5) { slots.get(i).draw(750, 302, g); }
					if(i == 6) { slots.get(i).draw(750, 448, g); }
					if(i == 7) { slots.get(i).draw(750, 594, g); }
					if(i == 8) { slots.get(i).draw(750, 740, g); }
					if(i == 9) { slots.get(i).draw(750, 864, g); }
					
					if(i == 10) { slots.get(i).draw(1180, 302, g); }
					if(i == 11) { slots.get(i).draw(1180, 448, g); }
					if(i == 12) { slots.get(i).draw(1180, 594, g); }
					if(i == 13) { slots.get(i).draw(1180, 740, g); }
					if(i == 14) { slots.get(i).draw(1180, 864, g); }
				}
				if(page == 1) {
					if(i == 15) { slots.get(i).draw(320, 302, g); }
					if(i == 16) { slots.get(i).draw(320, 448, g); }
					if(i == 17) { slots.get(i).draw(320, 594, g); }
					if(i == 18) { slots.get(i).draw(320, 740, g); }
					if(i == 19) { slots.get(i).draw(320, 864, g); }
					
					if(i == 20) { slots.get(i).draw(750, 302, g); }
					if(i == 21) { slots.get(i).draw(750, 448, g); }
					if(i == 22) { slots.get(i).draw(750, 594, g); }
					if(i == 23) { slots.get(i).draw(750, 740, g); }
					if(i == 24) { slots.get(i).draw(750, 864, g); }
					
					if(i == 25) { slots.get(i).draw(1180, 302, g); }
					if(i == 26) { slots.get(i).draw(1180, 448, g); }
					if(i == 27) { slots.get(i).draw(1180, 594, g); }
					if(i == 28) { slots.get(i).draw(1180, 740, g); }
					if(i == 29) { slots.get(i).draw(1180, 864, g); }
				}
				if(page == 2) {
					if(i == 30) { slots.get(i).draw(320, 302, g); }
					if(i == 31) { slots.get(i).draw(320, 448, g); }
					if(i == 32) { slots.get(i).draw(320, 594, g); }
					if(i == 33) { slots.get(i).draw(320, 740, g); }
					if(i == 34) { slots.get(i).draw(320, 864, g); }
					
					if(i == 35) { slots.get(i).draw(750, 302, g); }
					if(i == 36) { slots.get(i).draw(750, 448, g); }
					if(i == 37) { slots.get(i).draw(750, 594, g); }
					if(i == 38) { slots.get(i).draw(750, 740, g); }
					if(i == 39) { slots.get(i).draw(750, 864, g); }
					
					if(i == 40) { slots.get(i).draw(1180, 302, g); }
					if(i == 41) { slots.get(i).draw(1180, 448, g); }
					if(i == 42) { slots.get(i).draw(1180, 594, g); }
					if(i == 43) { slots.get(i).draw(1180, 740, g); }
					if(i == 44) { slots.get(i).draw(1180, 864, g); }
				}
				if(page == 3) {
					if(i == 45) { slots.get(i).draw(320, 302, g); }
					if(i == 46) { slots.get(i).draw(320, 448, g); }
					if(i == 47) { slots.get(i).draw(320, 594, g); }
					if(i == 48) { slots.get(i).draw(320, 740, g); }
					if(i == 49) { slots.get(i).draw(320, 864, g); }
					
					if(i == 50) { slots.get(i).draw(750, 302, g); }
					if(i == 51) { slots.get(i).draw(750, 448, g); }
					if(i == 52) { slots.get(i).draw(750, 594, g); }
					if(i == 53) { slots.get(i).draw(750, 740, g); }
					if(i == 54) { slots.get(i).draw(750, 864, g); }
					
					if(i == 55) { slots.get(i).draw(1180, 302, g); }
					if(i == 56) { slots.get(i).draw(1180, 448, g); }
					if(i == 57) { slots.get(i).draw(1180, 594, g); }
					if(i == 58) { slots.get(i).draw(1180, 740, g); }
					if(i == 59) { slots.get(i).draw(1180, 864, g); }
				}
			};
			buttonClose.draw(x+1148, y+74, g);
			buttonNext.draw(x+1100, y+780, g);
			buttonPrev.draw(x+1058, y+780, g);
		}
	}
	public void dispose() {
		setVisible(false);
		if(currentVendor == this) {
			currentVendor = null;
		}
	}
	public void setVendor(Npc npc) {
		vendor = npc;
		face = vendor.getFace().getScaledInstance(62, 62, BufferedImage.SCALE_FAST);
	}
	public void addItem(Item i) {
		Game.itemRegistry = new ItemRegistry();
		storage.add(i);
		slots.add(new GuiVendorSlot(i));
	}
	public void removeItem(Item item) {
		storage.remove(item);
		for (int i=0;i < slots.size();i++) {
			if(slots.get(i).getItem() == item) {
				slots.remove(slots.get(i));
			}
		}
	}
	public boolean sells(Item i) {
		return storage.contains(i);
	}
	public void setVisible(boolean b) {
		visible = b;
		if(b) {
			currentVendor = this;
		}
	}
	public boolean isVisible() {
		return visible;
	}
	public void setTitle(String str) {
		title = str;
	}
	public void click() {
		for (int i=0;i < slots.size();i++) {
			slots.get(i).click();
		}
	}
}
