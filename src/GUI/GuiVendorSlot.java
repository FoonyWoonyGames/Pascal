package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.InGame;
import Item.Item;
import Pascal.Game;
import Pascal.GamePanel;
import Util.ItemRegistry;
import Util.PColor;

public class GuiVendorSlot extends GuiMaster {
	private Item item;
	private GuiTooltipItem tooltip;
	private boolean hovered;
	private BufferedImage frame;
	private BufferedImage frameHovered;
	public GuiVendorSlot(Item i) {
		Game.itemRegistry = new ItemRegistry();
		item = i;
    	try {
    		frame = ImageIO.read(getClass().getResourceAsStream("/textures/gui/frame/vendor_item.png"));
    		frameHovered = ImageIO.read(getClass().getResourceAsStream("/textures/gui/frame/vendor_itemHovered.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	tooltip = new GuiTooltipItem(i);
		
	}
	public void draw(int x, int y, Graphics2D g) {
		if(GamePanel.mouseX > x && GamePanel.mouseX < x+frame.getWidth() &&
				GamePanel.mouseY > y && GamePanel.mouseY < y+frame.getHeight()) {
			hovered = true;
		}
		else {
			hovered = false;
		}
		g.setFont(new Font("Arial", Font.PLAIN, 22));
		if(hovered) {
			g.drawImage(frameHovered, null, x, y);
			drawColoredOutlinedString(item.getLocalizedName(), x+92, y+36, PColor.INTERFACE, PColor.BLACK, 1, g);
			if(!GuiTooltip.TTips.contains(tooltip)) {
				GuiTooltip.TTips.add(tooltip);
			}
		}
		else {
			if(GuiTooltip.TTips.contains(tooltip)) {
				GuiTooltip.TTips.remove(tooltip);
			}
			g.drawImage(frame, null, x, y);
			drawColoredOutlinedString(item.getLocalizedName(), x+92, y+36, item.getRankColor(), PColor.BLACK, 1, g);
		}
		g.setFont(new Font("Arial", Font.BOLD, 24));
		if(item.getPrice() == 0) {
			drawColoredOutlinedString("Free!", x+240, y+76, PColor.WHITE, PColor.BLACK, 1, g);
		} else {
			Color c = PColor.WHITE;
			if(item.getPrice() <= InGame.cash) c = PColor.GREEN;
			else c = PColor.DARK_RED;
			drawBackwardsColoredOutlinedString(item.getPrice() + "$", x+290, y+76, c, PColor.BLACK, 1, g);
		}
		g.drawImage(item.getSprite(), null, x+4, y+2);
	}
	public void setItem(Item i) {
		item = i;
    	tooltip = new GuiTooltipItem(i);
	}
	public Item getItem() {
		return item;
	}
	public boolean isEmpty() {
		return (item == null);
	}
	public void click() {
		if(hovered && InGame.cash >= item.getPrice()) {
			InGame.player.itemAdd(item);
			InGame.cash -= item.getPrice();
		}
	}
}
