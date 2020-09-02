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

public class NpcOldGeorge extends Npc {
	private HudDialogue dialogue = new HudDialogue("Old George Dialogue");
	private HudDialogueOption options = new HudDialogueOption("oldgeorge");
	public NpcOldGeorge(TileMap tm) {
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
		
		nameShort = "Old Man";
		nameLong = "Ol' George";
		imageid = "oldgeorge";

		damage = 5;
		health = healthMax = 100;
		setAttackRange(35);
		setAi(AIPASSIVEWANDERING);
		eyeRadius = 650;
		setInvincible(true);
		
		dialogue.setIcon("oldgeorge");
		dialogue.setName("Ol' George");
		dialogue.setText("Excuse me boy, can you please\nhelp me? I would walk to\nHeimdurnk myself, but I think that\nthey're patrolling the road.\nCan you please walk me there?");
		
		options.addOption("Sure thing!");
		options.addOption("Who are 'they'?");
		options.addOption("Where is Heimdurnk?");
		options.addOption("What are you doing here?");
		options.addOption("No, I don't have time, old man.");
		
		options.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setShowing(false);
				if(e.getID() == 1) {
					dialogue.setText("Thank you! Thank you!");
					InGame.objm.addObjective("0102");
					state = 10;
					ai.follow(InGame.player);
				}
				else if(e.getID() == 2) {
					dialogue.setText("What do you mean, 'who are they'?\nAren't you from Evigilant City?\nIt's all gone!");
					state = 20;
				}
				else if(e.getID() == 3) {
					dialogue.setText("Oh, it's just down this way to the\nright.");
					state = 30;
				}
				else if(e.getID() == 4) {
					dialogue.setText("I was just in Evigilant City, but\nI got out safe, fortunately.\nI see you did too.");
					state = 40;
				}
				else if(e.getID() == 5) {
					dialogue.setShowing(false);
					state = 50;
				}
			}});
		
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				if(state == 0) {
					state = 1;
					setAi(AIPASSIVESTILL);
				}
				else if(state == 1) {
					dialogue.nextPage();
					state = 2;
				}
				else if(state == 2) {
					dialogue.nextPage();
					state = 3;
				}
				else if(state == 3) {
					dialogue.nextPage();
					state = 4;
				}
				else if(state == 4) {
					options.setShowing(true);
					state = 5;
				}
				else if(state == 10) {
					dialogue.setShowing(false);
					state = 80;
				}
				else if(state == 20) {
					dialogue.nextPage();
					state = 21;
					if(!InGame.objm.containsObjective("0201")) {
						InGame.objm.addObjective("0201");
					}
				}
				else if(state == 21) {
					dialogue.setShowing(false);
					state = 50;
				}
				else if(state == 30) {
					dialogue.setShowing(false);
					state = 50;
				}
				else if(state == 40) {
					dialogue.nextPage();
					state = 41;
				}
				else if(state == 41) {
					dialogue.setShowing(false);
					state = 50;
				}
				else if(state == 50) {
					dialogue.setText("Won't you please help me?");
					dialogue.setShowing(true);
					options.setShowing(true);
				}
				else if(state == 80) {
					dialogue.setText("Yes? Do you have questions?");
					options = new HudDialogueOption("oldgeorge");
					options.addOption("Who are 'they'?");
					options.addOption("Where is Heimdurnk?");
					options.addOption("What are you doing here?");
					options.addOption("Let's go!");
					
					options.setUse(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							options.setShowing(false);
							if(e.getID() == 1) {
								dialogue.setText("What do you mean, who are 'they'?\nAren't you from Evigilant City?\nThe entire thing just got blown up!");
								state = 120;
							}
							else if(e.getID() == 2) {
								dialogue.setText("Oh, it's just down this way to the\nright.");
								state = 130;
							}
							else if(e.getID() == 3) {
								dialogue.setText("I was just in Evigilant City, but\nI got out safe, fortunately.\nI see you did too.");
								state = 140;
							}
							else if(e.getID() == 4) {
								dialogue.setText("Yes, I am right behind you!");
								getAi().follow(InGame.player);
								state = 130;
							}
						}});
					options.setShowing(true);
				}
				else if(state == 120) {
					dialogue.nextPage();
					state = 121;
					if(!InGame.objm.containsObjective("0201")) {
						InGame.objm.addObjective("0201");
					}
				}
				else if(state == 121) {
					dialogue.setShowing(false);
					state = 80;
				}
				else if(state == 130) {
					dialogue.setShowing(false);
					state = 80;
				}
				else if(state == 140) {
					dialogue.nextPage();
					state = 141;
				}
				else if(state == 141) {
					dialogue.setShowing(false);
					state = 80;
				}
				else if(state == 200) {
					dialogue.setText("Thank you very much! You go\non ahead, I will rest here for\na moment. Here, take this.\nYou're going to need it.");
					dialogue.setShowing(true);
					state = 201;
				}
				else if(state == 201) {
					dialogue.nextPage();
					state = 202;
				}
				else if(state == 202) {
					dialogue.nextPage();
					state = 203;
				}
				else if(state == 203) {
					InGame.objm.getObjective("0102").complete();
					getAi().stopFollowing();
					dialogue.setShowing(false);
					state = 250;
				}
				else if(state == 250) {
					dialogue.setText("Thank you so much for helping me.");
					dialogue.setShowing(true);
					state = 251;
				}
				else if(state == 251) {
					dialogue.setShowing(false);
					state = 250;
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
