package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Projectile extends MapObject {
	
	protected boolean hit;
	private boolean remove;
	protected BufferedImage[] sprites;
	protected BufferedImage[] hitSprites;
	public MapObject thisId;
	protected boolean facingRight;
	public static ArrayList<Projectile> Bullets = new ArrayList<Projectile>();
	protected double spraySpeed;
	protected int damage;
	
	public Projectile(TileMap tm, boolean right, MapObject id) {
		
		super (tm);
		Bullets.add(this);
		moveSpeed = 50;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		spraySpeed = 0;
		damage = 15;
		
		width = 240;
		height = 240;
		cwidth = 90;
		cheight = 30;
		thisId = id;
		this.facingRight = right;
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/attack/bullet.png"));
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			hitSprites = new BufferedImage[3];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(30);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void setHit() {
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(20);
		dx = 0;
		dy = 0;
		onHit();
	}
	
	public void onHit() {
	}
	
	public boolean shouldRemove() {return remove;}
	
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if(dx == 0 && !hit) {
			setHit();
		}
		if(dy == 0 && spraySpeed != 0 && !hit) {
			setHit();
		}
		if(!hit) {
			dy = spraySpeed;
		}
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
			Bullets.remove(this);
		}

		if (getx() < 0 || gety() < 0 || getx() > getTileMap().getWidth()-120 || gety() > getTileMap().getHeight()-120) {
			setHit();
		}
		
		
	}
	

	public void draw(Graphics2D g) {
		setMapPosition();
		
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2 - 8), null);

		}
		else {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2 - 8), -width, height, null);
		}
		
	}
	public int getDamage() {
		return damage;
	}
}
