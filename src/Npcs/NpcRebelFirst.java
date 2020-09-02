package Npcs;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Npc;
import Entity.Projectile;
import GameState.InGame;
import Item.ItemPaperWritten;
import Item.ItemTypeWeapon;
import Pascal.Game;
import Projectile.ProjectileShotgun;
import TileMap.TileMap;
import Zone.EvigilantCity01;

public class NpcRebelFirst extends Npc {

	protected static final int IDLE = 0;
	protected static final int WALKING = 1;
	protected static final int ATTACKING = 2;
	protected static final int SHOOTING = 3;
	public NpcRebelFirst(TileMap tm) {
		super(tm);
		
		width = 300;
		height = 240;
		cwidth = 60;
		cheight = 190;
		
		facingRight = true;
		moveSpeed = 1.0;
		maxSpeed = 5.6;
		stopSpeed = 1.2;
		
		fallSpeed = 0.6;
		maxFallSpeed = 24.0;
		jumpStart = -12.8;
		stopJumpSpeed = 1.2;
		
		nameShort = "Rebel";
		nameLong = "Rebel";
		imageid = "rebelFirst";

		damage = 2;
		health = healthMax = 80;
		setAi(AIAGGRESSIVESTILL);
		setAttackRange(750);
		getAi().setStopDistance(750);
		eyeRadius = 1750;
		setInvincible(false);
		
		attackRate = 1200;
		

		BufferedImage spritesheet = null;
	    try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/npc/" + imageid + ".png"));
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }

		try {
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[0]];
			for(int j = 0; j < numFrames[0]; j++) {
				bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
			}
			sprites.add(bi);
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		catch (Exception e) {}
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}

	boolean shooting = false;
	long lastShot;
	@Override
	public void attack() {
		shooting = true;
		lastShot = System.currentTimeMillis();
		Projectile a = new ProjectileShotgun(getTileMap(), facingRight, this);
		a.setPosition(getx(), gety());
		soundPlayer.playAt("item.weapon.shotgun.fire", getx(), gety());
		soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
		for(int i = 0; i < 2; i++) {
			InGame.player.fires.add(a);
		}
	}
	public void update() {
		super.update();
		if(attacking) {
			if(shooting && System.currentTimeMillis() - lastShot > 100) {
				shooting = false;
				animation.setFrames(sprites.get(ATTACKING));
			} else if(shooting){
				animation.setFrames(sprites.get(SHOOTING));
			}
			animation.setDelay(-1);
			width = 295;
		}
	}
	public void onDeath() {
		if(state == 1) {
			((EvigilantCity01) InGame.getZone()).end();
		}
	}

}
