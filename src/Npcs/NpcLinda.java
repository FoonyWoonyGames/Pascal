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
import Item.ItemCakePiece;
import TileMap.TileMap;
import Util.PAction;

public class NpcLinda extends Npc {
	private HudDialogue dialogue = new HudDialogue("Linda Dialogue");
	private HudDialogueOption options = new HudDialogueOption("linda");
	public NpcLinda(TileMap tm) {
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
		
		nameShort = "Linda";
		nameLong = "Linda";
		imageid = "linda";

		damage = 2;
		health = healthMax = 100;
		setAttackRange(35);
		setAi(AIPASSIVESTILL);
		eyeRadius = 650;
		setInvincible(false);
		drops.add(new ItemCakePiece());
		
		dialogue.setIcon("linda");
		dialogue.setName("Linda");
		dialogue.setText("Oh, hi there dear. Are you here\nfor cake?");
		
		options.addOption("What happened in Evigilant City?");
		options.addOption("Yes, as a matter of fact, I am.");
		
		options.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setShowing(false);
				if(e.getID() == 1) {
					dialogue.setText("Evigilant City? Oh, I'm sorry\nI can't talk about that right now.\nIt was terrible! .. I need a\nmoment, excuse me.");
					state = 10;
				}
				else if(e.getID() == 2) {
					if(!InGame.player.inventory.isFull()) {
						dialogue.setText("Here you go dear!");
						InGame.player.itemAdd(new ItemCakePiece());
						state = 30;
					} else {
						dialogue.setText("Your bag is full, I'm sorry.");
						state = 30;
					}
				}
			}});
		
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				if(state == 0) {
					dialogue.setText("Oh, hi there dear. Are you here\nfor cake?");
					state = 1;
				}
				else if(state == 1) {
					options.setShowing(true);
				}
				else if(state == 10) {
					dialogue.nextPage();
					state = 11;
				}
				else if(state == 11) {
					dialogue.nextPage();
					state = 12;
				}
				else if(state == 12) {
					dialogue.setShowing(false);
					state = 0;
				}
				else if(state == 20) {
					dialogue.setShowing(false);
					state = 0;
				}
				else if(state == 30) {
					dialogue.setShowing(false);
					state = 40;
				}
				else if(state == 40) {
					dialogue.setText("I'm sorry, I'm out of cake!");
					options = new HudDialogueOption("linda");
					options.addOption("What happened in Evigilant City?");
					options.setUse(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							options.setShowing(false);
							if(e.getID() == 1) {
								dialogue.setText("Evigilant City? Oh, I'm sorry\nI can't talk about that right now.\nIt was terrible! .. I need a\nmoment, excuse me.");
								state = 41;
							}
						}});
					options.setShowing(true);
				}
				else if(state == 41) {
					dialogue.nextPage();
					state = 42;
				}
				else if(state == 42) {
					dialogue.nextPage();
					state = 43;
				}
				else if(state == 43) {
					dialogue.setShowing(false);
					state = 40;
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
