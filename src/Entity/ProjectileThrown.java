package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class ProjectileThrown extends Projectile {
	
	protected boolean hit;
	private boolean remove;
	protected BufferedImage[] sprites;
	public MapObject thisId;
	protected boolean facingRight;
	public static ArrayList<ProjectileThrown> Bullets = new ArrayList<ProjectileThrown>();
	protected double spraySpeed;
	protected int damage;
	protected double lastDX;
	protected double lastDY;
	protected int bounce;
	protected double weight;
	
	public ProjectileThrown(TileMap tm, boolean right, MapObject id, int xSpeed, int ySpeed) {
		
		super (tm, right, id);
		Bullets.add(this);
		moveSpeed = xSpeed;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		dy = -ySpeed;
		
		
		spraySpeed = 0;
		damage = 15;
		bounce = 5;
		weight = 1;
		
		fallSpeed = 0.4 * weight;   // how long jump is & transition to max fallspeed
		maxFallSpeed = 10 * weight; // how quickly fall is 
		jumpStart = -17.6;  // how quick jump is
		stopJumpSpeed = 1.2; // short jump
		
		width = 240;
		height = 240;
		cwidth = 10;
		cheight = 10;
		thisId = id;
		this.facingRight = right;
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/attack/bullet.png"));
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
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
		animation.setDelay(20);
		dx = 0;
		dy = 0;
		if(bounce > 0) {
			double b = bounce / 100F;
			dx = lastDX * -b;
			dy = lastDY * -b;
			
			if(dx < 0.01 && dx > -0.01) {
				dx = 0;
			}
			if(dy < 0.01 && dy > -0.01) {
				dy = 0;
			}
		}
		onHit();
	}
	
	public void onHit() { 
	}
	
	public boolean shouldRemove() {return remove;}
	
	public void update() {
		System.out.println(falling);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if(dx != 0) {
			lastDX = dx;
		}
		if(dy != 0) {
			lastDY = dy;
		}
		if(dx == 0 && !hit) {
			setHit();
		}
		if(dy == 0 && spraySpeed != 0 && !hit) {
			setHit();
		}
		doGravity();
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
			Bullets.remove(this);
		}

		if (getx() < 0 || gety() < 0 || getx() > getTileMap().getWidth()-120 || gety() > getTileMap().getHeight()-120) {
			setHit();
		}
		if(isStill()) {
			hit = true;
		}
		
	}
	
	public void doGravity() {
		if(falling) {
			dy += fallSpeed;
		}
	}
	public boolean isStill() {
		return dx == 0 && dy == 0;
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2 - 8), null);

		}
		else {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2 - 8), -width, height, null);
		}
		bounce = 90;
		
	}
	public int getDamage() {
		return damage;
	}
}
