package Projectile;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Explosion;
import Entity.MapObject;
import Entity.Projectile;
import TileMap.TileMap;

public class ProjectileMagic extends Projectile {
	
	public ProjectileMagic(TileMap tm, boolean right, MapObject id, String type) {
		super (tm, right, id);
		moveSpeed = 20;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		damage = 1000;
		
		width = 240;
		height = 120;
		cwidth = 180;
		cheight = 60;
		thisId = id;
		this.facingRight = right;
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/attack/magic" + type + ".png"));
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			hitSprites = new BufferedImage[5];
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
			animation.setDelay(10);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onHit() {
		new Explosion(getTileMap(), getx(), gety(), 30);
	}
}
