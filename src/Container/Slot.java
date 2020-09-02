package Container;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import GUI.GuiTooltip;
import GUI.GuiTooltipItem;
import GameState.GameStateManager;
import GameState.InGame;
import Item.Item;
import Pascal.Game;
import Pascal.GamePanel;
import Util.PColor;

public class Slot {
	private Item item;
	private GuiTooltipItem tooltip;
	private boolean locked;
	private boolean hovered;
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean b) {
		locked = b;
	}
	public void setItem(Item i) {
		item = i;
		item.setSlot(this);
		tooltip = new GuiTooltipItem(i);
	}
	public void removeItem() {
		item.setSlot(null);
		item = null;
		tooltip = null;
	}
	public Item getItem() {
		return item;
	}
	public boolean isEmpty() {
		return item == null;
	}
	public void draw(int x, int y, Graphics2D g) {
		int mX = (int) GamePanel.mouseX;
		int mY = (int) GamePanel.mouseY;
		if(!isEmpty()) {
			g.drawImage(item.getSprite(), null, x, y);
			if(System.currentTimeMillis()-item.getLastUse() < item.getCooldown()) {
				Composite c = null;
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
				g.setComposite(c);
				g.setColor(PColor.DARK_GRAY);
				g.fillRect(x, y, 80, 80);
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
				g.setComposite(c);
				int remaining = (int) (100-(item.getCooldown()-(System.currentTimeMillis()-item.getLastUse()))*100/item.getCooldown());
				g.setColor(PColor.GRAY);
				g.fillRect(x+15, y+35, 50, 5);
				g.setColor(PColor.GREEN);
				g.fillRect(x+15, y+35, remaining/2, 5);
			}
		}
		else {
			if(GuiTooltip.TTips.contains(tooltip)) {
				GuiTooltip.TTips.remove(tooltip);
			}
		}
		if(mX > x && mX < x+80 && mY > y && mY < y+80) {
			hovered = true;
			Composite c = null;
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g.setComposite(c);
			g.setColor(PColor.INTERFACE);
			g.fillRect(x, y, 80, 80);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
			if(!isEmpty()) {
				if(!GuiTooltip.TTips.contains(tooltip)) {
					GuiTooltip.TTips.add(tooltip);
				}
			}
		}
		else {
			if(GuiTooltip.TTips.contains(tooltip)) {
				GuiTooltip.TTips.remove(tooltip);
			}
			hovered = false;
		}
	}
	public void rightClick() {
		if(!isEmpty() && !locked && hovered) {
			if(GameStateManager.isstShiftDown()) {
				getItem().drop(GamePanel.currentPlayer.getx(), GamePanel.currentPlayer.gety(), GamePanel.currentPlayer.getTileMap());
				getItem().playSound();
				GamePanel.currentPlayer.inventory.removeItem(item);
			}
		}
	}
	public void leftClick() {
		if(hovered) {
			if(isEmpty()) {
				if(InGame.holdsItem) {
					setItem(InGame.heldItem);
					getItem().playSound();
					InGame.holdsItem = false;
					InGame.heldItem = null;
				}
			}
			else {
				if(InGame.holdsItem) {
					Item i = getItem();
					setItem(InGame.heldItem);
					InGame.heldItem = i;
					i.playSound();
				}
				else {
					getItem().playSound();
					InGame.holdsItem = true;
					InGame.heldItem = getItem();
					removeItem();
				}
			}
		}
	}
	public boolean isHovered() {
		return hovered;
	}
}
