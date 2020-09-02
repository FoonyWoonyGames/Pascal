package Entity;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Item.Item;
import GUI.GuiMaster;
import GUI.GuiTooltip;
import GUI.GuiTooltipItem;
import Pascal.GamePanel;
import TileMap.TileMap;
import Util.PColor;

public class ItemEntity extends MapObject {

	private Item item;
	private boolean remove;
	private long TimeOfPickup;
	private long spawned;
	private int movey;
	private int tmpy;
	private BufferedImage image;
	private GuiMaster guim = new GuiMaster();
	private GuiTooltipItem tooltip;
	public static ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
	
	public ItemEntity(TileMap tm, Item item) {
		super(tm);
		entities.add(this);
		this.item = item;
		items.add(this);
		spawned = System.currentTimeMillis();
		
		tooltip = new GuiTooltipItem(item);
		image = item.getSprite();
		facingRight = true;
		fallSpeed = 0.8;
		maxFallSpeed = 10.0 * item.getWeight();
		width = 80;
		height = 80;
		cwidth = 80;
		cheight = 80;
	}
	public void pickUp() {
		remove();
		TimeOfPickup = System.currentTimeMillis();
		item.onPickup();
	}
	public boolean shouldRemove() {
		return remove;
	}
	public void remove() {
		remove = true;
		if(GuiTooltip.TTips.contains(tooltip)) {
			GuiTooltip.TTips.remove(tooltip);
		}
	}
	
	private void getNextPosition() {
		if(falling) {
			dy += fallSpeed;
		}
	}
	
	public void update() {
		if (!shouldRemove()) {
			getNextPosition();
			checkTileMapCollision();
			if(dy > getTileMap().getHeight()-130) {
				dy = 0;
			}
			setPosition(xtemp, ytemp);
		}
	}
	public void draw(Graphics2D g) {
		long currentTime = System.currentTimeMillis();
		int newx = (int)(x + xmap - width / 2);
		int newy = (int) (y + ymap - height / 2);
		if (!shouldRemove()) {
			setMapPosition();
			if(facingRight) {
				g.drawImage(
					image,
					(int)(x + xmap - width / 2),
					(int)(y + ymap - height / 2),
					null
				);
			}
			else {
				g.drawImage(
					image,
					(int)(x + xmap - width / 2 + width),
					(int)(y + ymap - height / 2),
					-width,
					height,
					null
				);
			}
			movey = newy;
			tmpy = 0;
			if(GamePanel.mouseX > (int)(x + xmap - width / 2) &&
					GamePanel.mouseX < (int)(x + xmap - width / 2) + width &&
					GamePanel.mouseY > (int)(y + ymap - height / 2) &&
					GamePanel.mouseY < (int)(y + ymap - height / 2) + height) {
				if(!GuiTooltip.TTips.contains(tooltip)) {
					GuiTooltip.TTips.add(tooltip);
				}
			}
			else {
				if(GuiTooltip.TTips.contains(tooltip)) {
					GuiTooltip.TTips.remove(tooltip);
				}
			}
		}
		else {
			if(currentTime - TimeOfPickup <= 1250 && GamePanel.showingHud()) {
				movey = newy + tmpy;
				Composite c;
				tmpy--;
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
				g.setComposite(c);
				if(newy - movey > 1) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
					g.setComposite(c);
				}
				if(newy - movey > 3) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
					g.setComposite(c);
				}
				if(newy - movey > 5) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
					g.setComposite(c);
				}
				if(newy - movey > 7) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g.setComposite(c);
				}
				if(newy - movey > 9) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
					g.setComposite(c);
				}
				if(newy - movey > 11) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
					g.setComposite(c);
				}
				if(newy - movey > 13) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
					g.setComposite(c);
				}
				if(newy - movey > 15) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
					g.setComposite(c);
				}
				if(newy - movey > 17) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
					g.setComposite(c);
				}
				if(newy - movey > 19) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
					g.setComposite(c);
				}
	
				if(newy - movey > 45) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
					g.setComposite(c);
				}
				if(newy - movey > 46) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
					g.setComposite(c);
				}
				if(newy - movey > 47) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
					g.setComposite(c);
				}
				if(newy - movey > 48) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
					g.setComposite(c);
				}
				if(newy - movey > 49) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
					g.setComposite(c);
				}
				if(newy - movey > 50) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g.setComposite(c);
				}
				if(newy - movey > 51) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
					g.setComposite(c);
				}
				if(newy - movey > 52) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
					g.setComposite(c);
				}
				if(newy - movey > 53) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
					g.setComposite(c);
				}
				if(newy - movey > 54) {
					c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f);
					g.setComposite(c);
					items.remove(this);
				}
				g.setFont(new Font("Arial", Font.BOLD, 36));
				guim.drawColoredOutlinedString("+" + item.getLocalizedName(), newx, movey, PColor.LIME, PColor.BLACK, 1, g);
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
				g.setComposite(c);
			}
		}
	}
	public long getTimeSpawned() {
		return spawned;
	}
	public Item getItem() {
		return item;
	}
}
