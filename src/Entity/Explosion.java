package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.InGame;
import Pascal.Game;
import TileMap.TileMap;
import Util.Sound;

public class Explosion extends MapObject {
	
	public static ArrayList<Explosion> Explosions = new ArrayList<Explosion>();
	private double power;
	private int radius;
	private long timeExploded;
	private Sound sound;
//	private BufferedImage img;
	public Explosion(TileMap tm, int x, int y, double p) {
		super(tm);	

//		try {
//			img = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Explosions.add(this);
		this.x = x;
		this.y = y;
		power = p;
		radius = (int) (p * 60);
		sound = new Sound();
		explode();
	}

	public void update() {
		setPosition(xtemp, ytemp);
	}
	public void explode() {
		sound.play("event.explosion");
		sound.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundMisc))));
		if(x > InGame.player.getx()) {
			if(x - InGame.player.getx() > 2500) {
				sound.setPan(1.0F);
			}
			else {
				Float pan = (Float.parseFloat(x + "") - InGame.player.getx()) / 2500.0F;
				sound.setPan(pan);
			}
		}
		else if(x < InGame.player.getx()) {
			if(InGame.player.getx() - x > 2500) {
				sound.setPan(-1.0F);
			}
			else {
				Float pan = -(InGame.player.getx() - Float.parseFloat(x + "")) / 2500.0F;
				sound.setPan(pan);
			}
		}
		else {
			sound.setPan(0.0F);
		}
		timeExploded = System.currentTimeMillis();
		for(int i = 0; i < MapObject.entities.size(); i++) {
			MapObject entity = MapObject.entities.get(i);
			if(entity.getx() > this.getx() - radius && entity.getx() < this.getx() + radius && entity.gety() > this.gety() - radius && entity.gety() < this.gety() + radius) {
				if(entity.getx() > this.getx()) {
					entity.push(DIRECTION_LEFT, (radius - (entity.getx() - getx())) / power);
				}
				else if(entity.getx() < this.getx()) {
					entity.push(DIRECTION_RIGHT, (radius - (getx() - entity.getx())) / power);
				}
				if(entity.gety() < this.gety()) {
					entity.push(DIRECTION_UP, (radius - (gety() - entity.gety())) / power);
				}
				else if(entity.gety() > this.gety()) {
					entity.push(DIRECTION_DOWN, (radius - (entity.gety() - gety())) / power);
				}
			}
		}
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		if(System.currentTimeMillis() - timeExploded > 5000) {
			Explosions.remove(this);
		}
		
	}

}
