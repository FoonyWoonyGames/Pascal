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
import HUD.HudDialogue;
import HUD.HudDialogueOption;
import Item.ItemKey;
import TileMap.TileMap;
import Util.PAction;

public class NpcKeykeeper extends Npc {
	private HudDialogue dialogue = new HudDialogue("Lord Keykeeper Dialogue");
	private HudDialogueOption options = new HudDialogueOption("keykeeper");
	private ItemKey keyBasement;
	public NpcKeykeeper(TileMap tm) {
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
		
		nameShort = "Keykeeper";
		nameLong = "Lord Keykeeper";
		imageid = "keykeeper";

		damage = 5;
		health = healthMax = 1000;
		setAttackRange(35);
		setAi(AIPASSIVESTILL);
		eyeRadius = 650;
		setInvincible(true);
		
		dialogue.setIcon("keykeeper");
		dialogue.setName("Lord Keykeeper");
		dialogue.setText("Hello, my name is Lord Keykeeper\n"
			+ "but you may call me Steven.\n"
			+ "I assume that you are here for the\n"
			+ "key, is that correct?");
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				if(state == 0) {
					dialogue.setShowing(true);
					ai.setStanding(true);
					state = 1;
				}
				else if (state == 1) {
					dialogue.nextPage();
					state = 2;
				}
				else if (state == 2) {
					dialogue.nextPage();
					options.setShowing(true);
					state = 3;
				}

				else if (state == 10) {
					dialogue.nextPage();
					state = 50;
				}
				else if (state == 20) {
					dialogue.nextPage();
					state = 50;
				}
				else if (state == 30) {
					dialogue.nextPage();
					state = 50;
				}
				else if (state == 50) {
					dialogue.setShowing(false);
					ai.setStanding(false);
					state = 100;
					drops.remove(0);
					give(keyBasement);
				}
				else if (state == 100) {
					dialogue.setShowing(true);
					ai.setStanding(true);
					dialogue.setText("What more do you want?! You got\nthe key! Bugger off!");
					state = 101;
				}
				else if (state == 101) {
					dialogue.setShowing(false);
					ai.setStanding(false);
					state = 100;
				}
			}
		});
		
		options.addOption("Yes, I am.");
		options.addOption("Uhm, no?");
		options.addOption("What are you doing?");
		
		options.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setShowing(false);
				if(e.getID() == 1) {
					dialogue.setText("That's what I thought! Heheh.\n...\nOh right, the key!");
					state = 10;
				}
				else if(e.getID() == 2) {
					dialogue.setText("No, you say?\nI don't believe you, my friend!\nHere, take it anyway!");
					state = 20;
				}
				else if(e.getID() == 3) {
					dialogue.setText("Oh, why I'm just here to give\nyou keys.\nErr, here you go!");
					state = 30;
				}
			}});
		
		keyBasement = new ItemKey();
		keyBasement.setKeyID("basementExit");
		keyBasement.setLocalizedName("Basement Key");
		keyBasement.setUnlocalizedName("keyBasement");
		addDrop(keyBasement);

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
		if(this.notOnScreen() && state == 100) {
			killNoDrop();
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
