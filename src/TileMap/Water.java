package TileMap;

import java.awt.Graphics2D;

import Entity.MapObject;
import Entity.Npc;
import Util.PColor;

public class Water {

	private int x;
	private int y;
	private int width;
	private int height;
	public Water(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	public void update() {
		for (int i=0;i < MapObject.entities.size();i++) {
			MapObject e = MapObject.entities.get(i);
			if(e.getx() > x && e.getx() < x + width && e.gety()-(e.getcheight()/2.5F) > y && e.gety() < y + height) {
				if(!e.inWater()) {
					e.setInWater(true);
					System.out.println("In water !!");
				}
			} else {
//				if(e.inWater()) {
//					e.setInWater(false);
//				}
			}
		}
	}
	public void draw(Graphics2D g) {
		g.setColor(PColor.WHITE);
		g.fillRect(x, y, width, height);
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setWidth(int w) {
		this.width = w;
	}
	public void setHeight(int h) {
		this.height = h;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
