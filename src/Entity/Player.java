package Entity;

import TileMap.*;
import Util.PAction;
import Util.PColor;
import Util.Sound;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import Container.InventoryPlayer;
import GUI.GuiFrame;
import GUI.GuiMaster;
import GameState.CharacterCustomization;
import GameState.GameStateManager;
import GameState.InGame;
import GameState.Menu;
import HUD.HudSplash;
import Pascal.Cheats;
import Pascal.Game;
import Pascal.GamePanel;
import Item.Item;
import Item.ItemTypeWeapon;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

public class Player extends Mob {
	
	// Player Properties
	private BufferedImage healthImage;
	private BufferedImage healthImageTemp;
	private int energy;
	private int maxEnergy;
	
	// Damage
	private boolean flinching;
	private long flinchTimer;
	public boolean dead;
	private long deadTime;
	
	// Projectile
	private boolean firing;
	private int fireDamage;
	public ArrayList<Projectile> fires;
	
	// Shadow
	private BufferedImage shadow;
	
	// Attack
	private boolean hitting;
	private int hitDamage;
	private int hitRange;
	private long hitCooldown;
	private long lastHit;
	
	private Animation animationTorso;
	
	// Inventory
	public InventoryPlayer inventory;
	public int maxItems = 35;

	
	// Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			2, // Idle
			2, // Walking
			1, // Jumping
			1, // Falling
			1, // Shooting 1H
			1, // Melee 1H
			1 // Shooting 2H
	};
	
	// Actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int SHOOTING_1H = 4;
	private static final int MELEE_1H = 5;
	private static final int SHOOTING_2H = 6;
//	private static final int SNEAKING = 6;
	
	public boolean sprinting;
	public boolean inmovement;
	private String flash;
	
	
	private int fallStart;
	private int fallEnd;
	private boolean inair;
	
	// Sounds
	private Sound soundFootsteps;
	
	// GUI
	private GuiMaster guimaster = new GuiMaster();
	
	public Player(TileMap tm) {
		super(tm);
		entities.add(this);
		
//		Game.activePlayers.add(this);
		
		width = 240;
		height = 240;
		cwidth = 60;
		cheight = 190;
		
		facingRight = true;
		// Remember: Change these and change noclip section too .. also sprinting
		moveSpeed = 1.0;
		maxSpeed = 8;
		stopSpeed = 0.12;
		
		fallSpeed = 0.6;   // how long jump is & transition to max fallspeed
		maxFallSpeed = 24.0; // how quickly fall is 
		jumpStart = -17.6;  // how quick jump is
		stopJumpSpeed = 1.2; // short jump
		
		health = healthMax = 100;
		energy = maxEnergy = 2500;
		fireDamage = 28;
		
		fires = new ArrayList<Projectile>();
		inventory = new InventoryPlayer();
		
		
		hitDamage = 8;
		hitRange = 160;
		hitCooldown = 600;
		
		inmovement = false;
		flash = "false";
		
		dead = false;


		shadow = null;
	    try {
			healthImageTemp = ImageIO.read(getClass().getResourceAsStream("/textures/hud/health.png"));
			healthImage = null;
			calculateHealthImage();
	    	shadow = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/shadow.png"));
	    } catch (IOException e) {
//	    	e.printStackTrace();
	    }
	    BufferedImage spritesheet;
	    BufferedImage body = null;
	    try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
			if(InGame.state == 0) {
		        body = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
			} else {
		        body = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/playerAfter.png"));
			}
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }

		try {
			spritesheet = body;
			if(CharacterCustomization.chosenSet != 0) {
	            spritesheet = joinBufferedImage(body, 
	            		CharacterCustomization.Set);
			}
			else {
	            spritesheet = joinBufferedImage(body, 
	            		CharacterCustomization.Feet, 
	            		null,//gloves? 
	            		CharacterCustomization.Glasses, 
	            		CharacterCustomization.Mask, 
	            		CharacterCustomization.Legs, 
	            		CharacterCustomization.Top, 
	            		CharacterCustomization.Overcoat, 
	            		CharacterCustomization.Hat);
			}
            
//            Player.joinBufferedImage(body, feet, gloves, facial, glasses, mask, legs, top, overcoat, hair, hat)
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
			sprites.add(bi);
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
		e.printStackTrace();
		}
		catch (Exception e) {
		}
		animation = new Animation();
		animationTorso = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		animationTorso.setFrames(sprites.get(SHOOTING_1H));
		
		soundFootsteps = new Sound();
	}
	public void itemAdd(Item item) {
		int carriedItems = inventory.length();
		if(carriedItems < maxItems) {
			item.playSound();
			inventory.addItem(item);
			item.onPickup();
		}
		else {
			item.playSound("gui.error");
			new HudSplash("Inventory is full", PColor.ERROR);
		}
	}
	public void itemRemove(Item item) {
		int carriedItems = inventory.length();
		if(carriedItems == 0) {
			System.err.println("Inventory contains 0 items, could not remove anything. Check code in Player");
		}
		else if(inventory.contains(item)) {
			item.drop(getx(), gety(), getTileMap());
			inventory.removeItem(item);
			item.playSound();
		}
		else {
			System.err.println(item.getLocalizedName() + " is not in player's inventory");
		}
	}
	public void useItem() {
		if(inventory.getItemFromHand() != null) {
			inventory.getItemFromHand().onUse();
		}
	}
	public static BufferedImage joinBufferedImage(BufferedImage body, BufferedImage feet, BufferedImage gloves, BufferedImage glasses, BufferedImage mask, BufferedImage legs, BufferedImage top, BufferedImage overcoat, BufferedImage hat) {
	        int wid = 2800;
	        int height = 1680;
	        //create a new buffer and draw two image into the new image
	        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = newImage.createGraphics();
	        g2.drawImage(body, 0, 0, null);
	        g2.drawImage(feet, 0, 0, null);
	        g2.drawImage(gloves, 0, 0, null);
	        g2.drawImage(glasses, 0, 0, null);
	        g2.drawImage(mask, 0, 0, null);
	        g2.drawImage(legs, 0, 0, null);
	        g2.drawImage(top, 0, 0, null);
	        g2.drawImage(overcoat, 0, 0, null);
	        g2.drawImage(hat, 0, 0, null);
	        g2.dispose();
	        return newImage;
	    }
	public static BufferedImage joinBufferedImage(BufferedImage body, BufferedImage set) {
        int wid = 2800;
        int height = 1680;
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        g2.drawImage(body, 0, 0, null);
        g2.drawImage(set, 0, 0, null);
        g2.dispose();
        return newImage;
    }

	public void Flash(boolean b) {
		flash = "true";
		maxSpeed = 16;
	}
	public void Superjump(boolean b) {
		maxFallSpeed = 10.0;
		jumpStart = -21.6;
		fallSpeed = 0.4;
}
	public void Nogravity(boolean b) {
		maxFallSpeed = 10.0;
		jumpStart = -21.6;
		stopJumpSpeed = 0.4;
}
	public void setSkin(String path) {
		String pathFixed = path.replace(".", "/");
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/" + pathFixed + ".png"));
			
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
				sprites.add(bi);
			}
			
		}
		catch(Exception e) {
		}
	}
	public void setUnderskin(String path) {
		String pathFixed = path.replace(".", "/");
	    BufferedImage spritesheet;
	    BufferedImage body = null;
	    try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
	        body = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/" + pathFixed + ".png"));
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }

		try {
			spritesheet = body;
			if(CharacterCustomization.chosenSet != 0) {
	            spritesheet = joinBufferedImage(body, 
	            		CharacterCustomization.Set);
			}
			else {
	            spritesheet = joinBufferedImage(body, 
	            		CharacterCustomization.Feet, 
	            		null,//gloves? 
	            		CharacterCustomization.Glasses, 
	            		CharacterCustomization.Mask, 
	            		CharacterCustomization.Legs, 
	            		CharacterCustomization.Top, 
	            		CharacterCustomization.Overcoat, 
	            		CharacterCustomization.Hat);
			}
            
//            Player.joinBufferedImage(body, feet, gloves, facial, glasses, mask, legs, top, overcoat, hair, hat)
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
			sprites.add(bi);
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
		e.printStackTrace();
		}
		catch (Exception e) {
		}
		animation = new Animation();
		animationTorso = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		animationTorso.setFrames(sprites.get(SHOOTING_1H));
	}
	public int getEnergy() { return energy; }
	public int getMaxEnergy() { return maxEnergy; }
	
	public void setFiring() {
		firing = true;
	}
	public void setHitting() {
		if(!hitting) {
			long hitTimePassed = System.currentTimeMillis() - lastHit;
			if(energy > 200 && hitTimePassed > hitCooldown) {
				hitting = true;
				energy = energy - 200;
				Sound soundPlayer = new Sound();
				if(!sprinting) {
					soundPlayer.play("misc.woosh01");
					soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundMisc))));
				} else {
					soundPlayer.play("misc.woosh01");
					soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundMisc))));
				}
				lastHit = System.currentTimeMillis();
				for(int i = 0; i < Npc.Npcs.size(); i++) {
					Npc npc = Npc.Npcs.get(i);

					if(hitting) {
						if(facingRight) {
							if(npc.getx() > x && npc.getx() < x + hitRange && npc.gety() > y - height / 2 && npc.gety() < y + height / 2) {
								if(!sprinting) {
									npc.hurt(hitDamage);
								}
								else {
									npc.hurt(hitDamage+5);
								}
							}
						}
						else {
							if(npc.getx() < x && npc.getx() > x - hitRange && npc.gety() > y - height / 2 && npc.gety() < y + height / 2) {
								if(!sprinting) {
									npc.hurt(hitDamage);
								}
								else {
									npc.hurt(hitDamage+5);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void checkAttack() {
		for(int i = 0; i < Npc.Npcs.size(); i++) {
			Npc npc = Npc.Npcs.get(i);

			for(int j = 0; j < fires.size(); j++) {
				if(fires.get(j).intersects(npc) && fires.get(j).thisId != npc && !npc.isDead() && !fires.get(j).hit && fires.get(j).getTileMap() == npc.getTileMap()) {
					npc.hurt(fires.get(j).getDamage());
					fires.get(j).setHit();
					break;
				}
			}
			
		}
	}
	
	private void getNextPosition() {
		
		//movement
		if(!noclip && !flying && !sprinting && !swimming) {
			maxSpeed = 8;
			moveSpeed = 1.0;
			stopSpeed = 1.2;
		}
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		if(!noclip && !flying && !swimming) {
			//jumping
			if(jumping && !falling) {
				dy = jumpStart;
				falling = true;
			}
			//falling
			if(falling) { 
				
				dy += fallSpeed;
				
				if (dy > 0) jumping = false;
				if (dy < 0 && !jumping) dy += stopJumpSpeed;
				
				if (dy > maxFallSpeed) dy = maxFallSpeed;
			}
		}
		else {
			if(noclip) {
				maxSpeed = 20;
				moveSpeed = 20;
				stopSpeed = 2;
			}
			if(jumping) {
				dy -= moveSpeed;
				if(dy < -maxSpeed*1.2F) {
					dy = -maxSpeed*1.2F;
				}
			}
			else {
				if(dy < 0) {
					dy += stopSpeed;
					if(dy > 0) {
						dy = 0;
					}
				}
			}
			if(down) {
				dy += moveSpeed;
				if(dy > maxSpeed) {
					dy = maxSpeed;
				}
			}
			else {
				if(dy > 0) {
					dy -= stopSpeed;
					if(dy < 0) {
						dy = 0;
					}
				}
			}
		}

		if(!noclip) {
			for (int i=0;i < InGame.getZone().wallsInvisible.size();i++) {
				String[] props = InGame.getZone().wallsInvisible.get(i).split("=");
				if(getx() > Integer.parseInt(props[1]) - 25 && getx() < Integer.parseInt(props[1]) && facingRight) {
					dx = 0;
				}
				if(getx() < Integer.parseInt(props[1]) + 25 && getx() > Integer.parseInt(props[1]) && !facingRight) {
					dx = 0;
				}
			}
		}
	}

	public void setSprinting(boolean b) {
		if (b) {
			if(!jumping && !falling && !noclip && !flying && !swimming) {
				sprinting = b;
			}
		}
		else {
			sprinting = b;
		}
	}
	public boolean isSprinting() {
		return sprinting;
	}

	public void itemEffects() {
		for (int i=0;i < inventory.length();i++) {
			inventory.get(i).onEquip();
		}
		if(getHeldItem() != null) {
			getHeldItem().onHold();
		}
	}
	int t = 0;
	public void update() {
		// Update pos
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		GamePanel.currentPlayer = this;
		checkAttack();
		itemEffects();
		inventory.update();
		
		if(inWater()) {
			swimming = true;
		} else {
			swimming = false;
		}
		
		// AUTOJUMP FOR MOBS -Don't activate unless testing <3-
//		if(currentAction == WALKING && xdest != x) {
//			setJumping(true);
//		}

		if(dy != 0 && !inair){
			inair = true;
			fallStart = gety();
		}
		else if(inair && dy == 0 && !swimming) {
			inair = false;
			fallEnd = gety();
			int fallLength = fallEnd - fallStart;
			if (fallLength > 800) {
				hurt(fallLength / 160);
			}
			fallStart = 0;
			fallEnd = 0;
		}
		
		if(energy < maxEnergy) {
			if(!sprinting) {
				energy = energy + 2;
			}
		}
		t++;
		if(energy < 1) {
			setSprinting(false);
			if(t > 100) {
				new HudSplash("Not enough energy", PColor.ERROR);
				t = 0;
			}
		}
		if(energy > maxEnergy || invincible) {
			energy = maxEnergy;
		}
		if (sprinting && energy != 0 && inmovement) {
			energy = energy - 10;
		}
		else if (sprinting && !inmovement) {
			energy = energy + 2;
		}
		
		if(health <= 0 && !dead) {
			health = 0;
			InGame.getZone().getAmbient().stop();
			deadTime = System.currentTimeMillis();
			InGame.hudFade.fade(PColor.BLACK, 2);
			dead = true;
		}
		if(health > 100) {
			health = 100;
		}
		if(gety() > getTileMap().getHeight()-150) {
			health = 0;
			dy = 0;
		}
		if (getx() < 0 || gety() < 0 || getx() > getTileMap().getWidth()-120) {
			GameStateManager.setState(GameStateManager.MENUSTATE);
			GuiFrame frame;
			frame = new GuiFrame("nonexistantlocation");
			frame.setTitle("Non-existant location!");
			frame.setText("You went outside of the map, to a\nnon-existant location, which would have\ncrashed the game, but instead it showed\nthis message!");
			frame.setWidth(800);
			frame.setHeight(260);
			frame.setPosition(960, 260);
			frame.setVisible(true);
			Menu.displayFrame(frame);
		}
		

		if (sprinting) {
			maxSpeed = 12;
		}
		if (!sprinting) {
			if(flash.equalsIgnoreCase("false") && !noclip) {
				maxSpeed = 8;
			}
		}
		
		// Check for attack finish
		if (currentAction == MELEE_1H) {
			if(animationTorso.hasPlayedOnce()) hitting = false;
		}
		if (currentAction == SHOOTING_1H || currentAction == SHOOTING_2H) {
			if(((ItemTypeWeapon) getHeldItem()).getAnimation().hasPlayedOnce()) {
				firing = false;
			}
		}
		
		// Bullet attack
		if(firing) {
			if(currentAction != SHOOTING_1H && currentAction != SHOOTING_2H) {
				for(int i = 0; i < ((ItemTypeWeapon) getHeldItem()).getShots(); i++) {
					fires.add(((ItemTypeWeapon) getHeldItem()).getProjectile(getTileMap(), this.facingRight, this));
				}
				
			}
		}
		
		// Entity collision
		for (int i=0;i < fires.size();i++) {
			if (intersects(fires.get(i)) && fires.get(i).thisId != this) {
				hurt(10);
				fires.get(i).setHit();
			}
		}

		// update fires
		for(int i = 0; i < fires.size(); i++) {
			fires.get(i).update();
			if(fires.get(i).shouldRemove()) {
				fires.remove(i);
				i--;
			}
		}
		
		// set animation
		if(firing) {
			if(currentAction != SHOOTING_1H && currentAction != SHOOTING_2H) {
				ItemTypeWeapon held = (ItemTypeWeapon) getHeldItem();
				if(held.isTwoHanded()) {
					currentAction = SHOOTING_2H;
					animationTorso.setFrames(sprites.get(SHOOTING_2H));
				} else {
					currentAction = SHOOTING_1H;
					animationTorso.setFrames(sprites.get(SHOOTING_1H));
				}
				width = 240;
				animationTorso.setDelay(-1);
			}
		}
		else if(hitting) {
			if(currentAction != MELEE_1H) {
				currentAction= MELEE_1H;
				width = 240;
				
				animationTorso.setFrames(sprites.get(SHOOTING_1H));
				animationTorso.setDelay(150);
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(-1);
				width = 240;
				inmovement = true;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 240;
				inmovement = true;
			}
	}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(150);
				width = 240;
				inmovement = true;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(200);
				width = 240;
				inmovement = false;
			}
		}
		if(swimming) {
			animation.setFrames(sprites.get(JUMPING));
			animation.setDelay(-1);
			width = 240;
			inair = true;
		}
		
		animation.update();
		animationTorso.update();
			
			if(right) facingRight = true;
			if(left) facingRight = false;
			
	}
	public void calculateHealthImage() {
		if(getHealth() > 0 && getHealth() <= getMaxHealth()) {
			healthImage = healthImageTemp.getSubimage(0, 0,  (int) (getHealth()*4.96), healthImageTemp.getHeight());
		}
	    if(getHealth() < 50 && getHealth() > 0) {
	    	healthImage = PColor.getColorized(healthImage, new Color(100-(getHealth()), getHealth()*3, 0));
	    }
	    else {
	    	healthImage = PColor.getColorized(healthImage, new Color(20, 150, 20));
	    }
	}
	public BufferedImage getHealthImage() {
		return healthImage;
	}
	public void hurt(int dmg) {
		if(flinching || isInvincible() || noclip || flying) return;
		super.hurt(dmg);
		if(isEssential() && health < 1) {
			health = 1;
		}
		calculateHealthImage();
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	public void heal(int hp) {
		health = health + hp;
		calculateHealthImage();
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	public void setHealth(int hp) {
		health = hp;
		calculateHealthImage();
	}
	public void setEnergy(int en) {
		energy = en;
		if(energy > 2500) {
			energy = 2500;
		}
	}
	public void stop() {
		setLeft(false);
		setRight(false);
		setJumping(false);
	}
	public void drawPlayer(int x, int y, Graphics2D g) {
		ItemTypeWeapon held = null;
		if(getHeldItem() != null && getHeldItem().isValidWeapon()) {
			held = (ItemTypeWeapon) getHeldItem();
		}
		if(flinching && !invisible || !dead && !invisible) {
			if (Game.settings.shadows) {
				if (!inair) {
					g.drawImage(shadow, x + 62, y + 214, null);
				}
			}
			if(facingRight) {
				if(!firing && !hitting) {
					g.drawImage(animation.getImage(), x, y, null);
				}
				else {
					g.drawImage(animation.getImage().getSubimage(0, 180, 240, 60), x, y + 180, null);
					g.drawImage(animationTorso.getImage().getSubimage(0, 0, 240, 180), x, y, null);
					if(getHeldItem() != null && getHeldItem().isValidWeapon() && held.getAnimation().getImage() != null) {
						g.drawImage(held.getAnimation().getImage(), x+held.getSpriteX(), y+held.getSpriteY(), null);
						held.getAnimation().update();
					}
				}
			}
			else {
				if(!firing && !hitting) {
					g.drawImage(animation.getImage(), x + width, y, -width, height, null);
				}
				else {
					g.drawImage(animation.getImage().getSubimage(0, 180, 240, 60), x + width, y + 180, -width, 60, null);
					g.drawImage(animationTorso.getImage().getSubimage(0, 0, 240, 180), x + width, y, -width, 180, null);
					if(getHeldItem() != null && getHeldItem().isValidWeapon()) {
						g.drawImage(held.getAnimation().getImage(), x-held.getSpriteX()+width, y+held.getSpriteY(), 
								-held.getAnimation().getImage().getWidth(), 
								held.getAnimation().getImage().getHeight(), null);
						held.getAnimation().update();
					}
				}
			}
		}
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		
		calculateHealthImage();
		
		for(int i = 0; i < fires.size(); i++) {
			fires.get(i).draw(g);
		}
		
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 50 % 2 == 0) {
				return;
			}
			if(elapsed > 125) {
				flinching = false;
			}
		}

		drawPlayer((int)(x + xmap - width / 2), (int)(y + ymap - height / 2 - 8), g);
		if (Game.settings.shownicknames && GamePanel.showingHud()) {
			drawName(g);
		}
//		drawRectangle(g);

	}
	public void drawName(Graphics2D g) {
	    g.setFont(new Font("Arial", Font.BOLD, 30));
		guimaster.drawCenteredColoredOutlinedString(Game.username, (int)(x + xmap - width / 2) + width / 2, (int) (int) (y + ymap - height / 2), PColor.WHITE, PColor.BLACK, 1, g);
	}
	public boolean handEmpty() {
		return inventory.getHand() == 0;
	}
	public Item getHeldItem() {
		return inventory.getItemFromHand();
	}
	public boolean isDead() {
		return dead;
	}
	public long getDeathTime() {
		return deadTime;
	}
}