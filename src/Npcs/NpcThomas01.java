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
import GUI.GuiNotification;
import GameState.InGame;
import HUD.HudDialogue;
import HUD.HudDialogueOption;
import TileMap.TileMap;
import Util.PAction;
import Util.PColor;
import Zone.Heimdurnk02;

public class NpcThomas01 extends Npc {
	private HudDialogue dialogue = new HudDialogue("Thomas Dialogue");
	private HudDialogueOption options = new HudDialogueOption("thomas");
	public NpcThomas01(TileMap tm) {
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
		
		nameShort = "Thomas";
		nameLong = "Thomas";
		imageid = "thomas01";

		damage = 2;
		health = healthMax = 100;
		setAttackRange(35);
		setAi(AIPASSIVESTILL);
		eyeRadius = 650;
		setInvincible(true);
		
		dialogue.setIcon("thomas");
		dialogue.setName("Thomas");
		dialogue.setText("Hi there.");
		
		options.addOption("What happened in Evigilant City?");
		
		options.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setShowing(false);
				if(e.getID() == 1) {
					dialogue.setText("Do you mean last week? Oh it was\nterrible, the rebels massacred half\nthe city. It went on for hours upon\nhours. Haven't you heard about\nthat? Where've you been?");
					state = 2;
				}
			}});
		
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				if(state == 0) {
					dialogue.setText("Yeah?");
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
					dialogue.nextPage();
					state = 4;
				}
				else if(state == 4) {
					dialogue.nextPage();
					state = 5;
				}
				else if(state == 5) {
					options = new HudDialogueOption("thomas");
					options.addOption("I don't recall hearing this.");
					options.setUse(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							options.setShowing(false);
							if(e.getID() == 1) {
								dialogue.setIcon("pascal");
								dialogue.setName("Pascal");
								dialogue.setText("No, my memory is a little\nblurred. I'm from Evigilant City,\nbut I have no idea what happened.");
								state = 6;
							}
						}});
					options.setShowing(true);
				}
				else if(state == 6) {
					dialogue.nextPage();
					state = 7;
				}
				else if(state == 7) {
					dialogue.setIcon("thomas");
					dialogue.setName("Thomas");
					dialogue.setText("You have no idea? Wow.\nHow about this, I'll walk you to\nthe outskirts of the city and I'll\ntell you a bit about it. Does that\nsound good to you?");
					state = 8;
				}
				else if(state == 8) {
					dialogue.nextPage();
					state = 9;
				}
				else if(state == 9) {
					dialogue.nextPage();
					state = 10;
				}
				else if(state == 10) {
					dialogue.nextPage();
					state = 11;
				}
				else if(state == 11) {
					dialogue.setIcon("pascal");
					dialogue.setName("Pascal");
					dialogue.setText("Outskirts? Why don't we go in\nthere?");
					state = 12;
				}
				else if(state == 12) {
					dialogue.setIcon("thomas");
					dialogue.setName("Thomas");
					dialogue.setText("Because the rebels might still be\nin there bud. The police might\nhave the city under control, but\nthat doesn't mean it's safe. I want\nto make it back here, so let's\nstick to the outskirts, okay?");
					state = 13;
				}
				else if(state == 13) {
					dialogue.nextPage();
					state = 14;
				}
				else if(state == 14) {
					dialogue.nextPage();
					state = 15;
				}
				else if(state == 15) {
					dialogue.nextPage();
					state = 16;
				}
				else if(state == 16) {
					dialogue.nextPage();
					state = 17;
				}
				else if(state == 17) {
					dialogue.setText("You tell me when you're ready.");
					state = 18;
				}
				else if(state == 18) {
					dialogue.setShowing(false);
					state = 20;
					if(InGame.objm.containsObjective("0201") && InGame.objm.getObjective("0201").getState() == 0) {
						InGame.objm.getObjective("0201").nextState();
					}
				}
				else if(state == 20) {
					dialogue.setShowing(true);
					dialogue.setText("Are you ready to go?");
					options = new HudDialogueOption("thomas");
					options.addOption("Yes, let's go.");
					options.addOption("No, I'm not ready yet.");
					options.setUse(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							options.setShowing(false);
							if(e.getID() == 1) {
								dialogue.setText("Alright then!");
								state = 30;
								if(InGame.objm.containsObjective("0201") && InGame.objm.getObjective("0201").getState() == 1) {
									InGame.objm.getObjective("0201").nextState();
								}
							}
							if(e.getID() == 2) {
								dialogue.setText("Sure, take your time.");
								state = 40;
							}
						}});
					options.setShowing(true);
				}
				else if(state == 30) {
					InGame.hudFade.fade(PColor.BLACK, 0.5);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					InGame.setZone(6);
					dialogue.setShowing(false);
					state = 20;
				}
				else if(state == 40) {
					dialogue.setShowing(false);
					state = 20;
				}
				else if(state == 50) {
					dialogue.setText("Take a look around, see if\nanything helps your memory.");
					state = 51;
				}
				else if(state == 51) {
					dialogue.setShowing(false);
					state = 50;
				}
				else if(state == 100) {
					dialogue.setText("Did you find something?");
					InGame.player.turnAround(true);
				}
				else if(state == 101) {
					dialogue.setIcon("pascal");
					dialogue.setName("Pascal");
					dialogue.setText("Yeah... This is my little si--");
					state = 102;
				}
				else if(state == 102) {
					turnAround(true);
					dialogue.setIcon("rebelFirst");
					dialogue.setName("Rebel");
					dialogue.setText("Hey! You two!");
					state = 103;
				}
				else if(state == 103) {
					dialogue.setIcon("thomas");
					dialogue.setName("Thomas");
					dialogue.setText("Crap! It's a rebel! Run, dude!");
					state = 104;
				}
				else if(state == 104) {
					setLeft(true);
					dialogue.setIcon("rebelFirst");
					dialogue.setName("Rebel");
					dialogue.setText("I'll kill you!");
					state = 105;
				}
				else if(state == 105) {
					dialogue.setShowing(false);
					GuiNotification tip = new GuiNotification();
					tip.setTitle("Tip!");
					tip.setDescription("Use 'Q' to attack!");
					tip.setIcon("gui.icon.tip");
					GuiNotification.Notify(tip);
					state = 106;
				}
				else if(state == 106) {
					dialogue.setIcon("thomas");
					dialogue.setName("Thomas");
					dialogue.setText("Run!");
					dialogue.setShowing(true);
					state = 107;
				}
				else if(state == 107) {
					dialogue.setShowing(false);
					state = 106;
				}
				else if(state == 110) {
					dialogue.setIcon("thomas");
					dialogue.setName("Thomas");
					dialogue.setText("Shit, did you kill him?!");
					dialogue.setShowing(true);
				}
				else if(state == 111) {
					dialogue.setText("Come on, let's get out of here\nbefore more appear.");
					state = 112;
				}
				else if(state == 112) {
					InGame.hudFade.fade(PColor.BLACK, 0.5);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					InGame.setZone(5);
					InGame.player.setPosition(4430, 3980);
					((Heimdurnk02) InGame.getZone()).changeThomas();
					dialogue.setShowing(false);
				}
				else if(state == 120) {
					dialogue.setIcon("thomas");
					dialogue.setName("Thomas");
					dialogue.setText("That was insane. Let's stay\nhere for a while.");
					dialogue.setShowing(true);
					state = 121;
				}
				else if(state == 121) {
					dialogue.setShowing(false);
					state = 120;
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
		if(state == 104 && x < 140) {
			setLeft(false);
			killNoDrop();
		}
//		if(currentAction == WALKING && xdest != x) {
//			setJumping(true);
//		}
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
