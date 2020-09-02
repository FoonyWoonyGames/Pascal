package Projectile;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import TileMap.TileMap;

public class ProjectilePistol extends Projectile {
	
	public ProjectilePistol(TileMap tm, boolean right, MapObject id) {
		super (tm, right, id);
		moveSpeed = 80;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		damage = 15;
		
		width = 240;
		height = 240;
		cwidth = 90;
		cheight = 30;
		thisId = id;
		this.facingRight = right;
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/attack/bulletPistol.png"));
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
