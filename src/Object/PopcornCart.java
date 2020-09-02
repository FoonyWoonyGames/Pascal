package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GUI.GuiMaster;
import TileMap.TileMap;

public class PopcornCart extends MapObject {
	private BufferedImage icon;

	public PopcornCart(TileMap tm) {
		super(tm);
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/popcorncart.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		g.drawImage(icon, (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
	}
}
