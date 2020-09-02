package Npcs;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Npc;
import Entity.Player;
import GameState.InGame;
import HUD.HudDialogue;
import HUD.HudDialogueOption;
import TileMap.TileMap;
import Util.PAction;

public class NpcJohnny extends Npc {
	private HudDialogue dialogue = new HudDialogue("Johnny Dialogue");
	private HudDialogueOption options = new HudDialogueOption("johnny");
	public NpcJohnny(TileMap tm) {
		super(tm);
		
		width = 240;
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
		
		nameShort = "Johnny";
		nameLong = "Johnny";
		imageid = "johnny";

		damage = 2;
		health = healthMax = 100;
		setAttackRange(35);
		setAi(AIPASSIVESTILL);
		eyeRadius = 650;
		setInvincible(false);
		
		dialogue.setIcon("johnny");
		dialogue.setName("Johnny");
		dialogue.setText("What are you doing in my\nmom's house?");
		
		options.addOption("What happened in Evigilant City?");
		
		options.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setShowing(false);
				if(e.getID() == 1) {
					dialogue.setText("I don't freakin' know, I\nthink the rebels killed a bunch\nof people or something.");
					state = 2;
				}
			}});
		
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				if(state == 0) {
					dialogue.setText("What are you doing in my\nmom's house?");
					state = 1;
				}
				else if(state == 1) {
					options.setShowing(true);
				}
				else if(state == 2) {
					dialogue.nextPage();
					state = 3;
				}
				else if(state == 3) {
					dialogue.setShowing(false);
					state = 0;
				}
			}
		});

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
	public void update() {
		super.update();
		if(getAi().isFollowing(InGame.player)) {
			if(InGame.player.getx() > getx() + 2000 || InGame.player.getx() < getx() - 2000) {
				setPosition(InGame.player.getx(), InGame.player.gety());
			}
		}
	}
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	public void use() {
		super.use();
		if(!dead) {
			dialogue.setShowing(true);
			dialogue.use();
		}
	}
	public void attack() {
		((Player) ai.getTarget()).hurt(getDamage());
	}
	
}
