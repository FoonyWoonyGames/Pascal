package Projectile;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import TileMap.TileMap;
import Util.PInteger;

public class ProjectileShotgun extends Projectile {
	
	public ProjectileShotgun(TileMap tm, boolean right, MapObject id) {
		super (tm, right, id);
		moveSpeed = 60;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		damage = 20;

		
		width = 240;
		height = 240;
		cwidth = 20;
		cheight = 4;
		thisId = id;
		this.facingRight = right;
		
		int proc = PInteger.randomProcent();
		double ran = PInteger.randomDouble();
		int ten = PInteger.randomBetween(30, 0);
		spraySpeed = ten+ran;
		if(proc > 50) {
			spraySpeed = -spraySpeed;
		}
		dy = 0.01;
		
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/attack/bulletShotgun.png"));
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
