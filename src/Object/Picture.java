package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GUI.GuiMaster;
import TileMap.TileMap;

public class Picture extends MapObject {
	private BufferedImage image;
	private String variation;
	private BufferedImage icon;
	private GuiMaster gui;
	
	public final static int STANDING = 0;
	public final static int HANGING = 1;

	public Picture(TileMap tm) {
		super(tm);
		variation = "01";
		gui = new GuiMaster();
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/image" + variation + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(icon, (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		g.drawImage(image, (int)(x + xmap - width / 2) + 5, (int)(y + ymap - height / 2) + 47, null);
	}
	public void setVariation(int v) {
		variation = Integer.toString(v);
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/image" + variation + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setImage(String img) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/object/images/" + img+ ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
