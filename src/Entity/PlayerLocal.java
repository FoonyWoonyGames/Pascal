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
import GameState.InGameLocal;
import GameState.Menu;
import HUD.HudSplash;
import Pascal.Cheats;
import Pascal.Game;
import Pascal.GamePanel;
import Item.Item;
import Item.ItemCakePiece;
import Item.ItemPistol;
import Item.ItemTypeConsumable;
import Item.ItemTypeWeapon;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

public class PlayerLocal extends Mob {
	
	// Damage
	private boolean flinching;
	public boolean dead;
	private long deadTime;
	private long flinchTimer;
	public int deaths;
	
	// Projectile
	private boolean firing;
	private int fireDamage;
	
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
	public Item heldItem;
	
	public String name;

	
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
	
	public PlayerLocal(TileMap tm, String n) {
		super(tm);
		name = n;
		
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
		fireDamage = 28;
		
		
		hitDamage = 8;
		hitRange = 160;
		hitCooldown = 600;
		
		inmovement = false;
		flash = "false";
		
		dead = false;


		shadow = null;
	    try {
	    	shadow = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/shadow.png"));
	    } catch (IOException e) {
//	    	e.printStackTrace();
	    }
	    BufferedImage spritesheet;
	    BufferedImage body = null;
	    try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
		    body = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
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
		if(heldItem != null) {
			heldItem.drop(getx(), gety(), getTileMap());
		}
		item.playSound();
		heldItem = item;
		item.onPickup();
	}
	public void itemRemove(Item item) {
		if(heldItem != null) {
			heldItem.drop(getx(), gety(), getTileMap());
			heldItem.playSound();
			heldItem = null;
		}
	}
	public void useItem() {
		if(heldItem != null) {
			if(!heldItem.onCooldown()) {
				heldItem.lastUse();
				if(heldItem.isValidWeapon() && ((ItemTypeWeapon) heldItem).getAmmo() != 0) {
					setFiring();
				}
				if(heldItem.isValidFood()) {
					heal(((ItemTypeConsumable) heldItem).getFoodPoints());
					heldItem = null;
				}
				heldItem.onUse();
			}
		} else {
			setHitting();
		}
	}
	public void setHitting() {
		if(!hitting) {
			long hitTimePassed = System.currentTimeMillis() - lastHit;
			if(hitTimePassed > hitCooldown) {
				hitting = true;
				Sound soundPlayer = new Sound();
				soundPlayer.play("misc.woosh01");
				soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundMisc))));
				lastHit = System.currentTimeMillis();

				for(int i = 0; i < InGameLocal.players.size(); i++) {
					PlayerLocal player = InGameLocal.players.get(i);

					if(hitting) {
						if(facingRight) {
							if(player != this && player.getx() > x && player.getx() < x + hitRange && player.gety() > y - height / 2 && player.gety() < y + height / 2) {
								player.hurt(hitDamage);
							}
						}
						else {
							if(player != this && player.getx() < x && player.getx() > x - hitRange && player.gety() > y - height / 2 && player.gety() < y + height / 2) {
								player.hurt(hitDamage);
							}
						}
					}
				}
			}
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
	
	public void setFiring() {
		firing = true;
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
		if(heldItem != null) {
			heldItem.onHold();
			heldItem.onEquip();
		}
	}
	int t = 0;
	public void update() {
		// Update pos
		getNextPosition();
		checkTileMapCollision();
		itemEffects();
		setPosition(xtemp, ytemp);
		if(dy != 0) {
			inair = true;
		} else {
			inair = false;
		}
		if(gety() > getTileMap().getHeight()-150) {
			health = 0;
			dy = 0;
		}
		
		if(health <= 0) {
			deadTime = System.currentTimeMillis();
			hurt(health);
			dead = true;
			deaths++;
			heldItem = null;
		}
		// Bullet attack
		if(firing) {
			if(currentAction != SHOOTING_1H && currentAction != SHOOTING_2H) {
				for(int i = 0; i < ((ItemTypeWeapon) heldItem).getShots(); i++) {
					InGameLocal.projectiles.add(((ItemTypeWeapon) heldItem).getProjectile(getTileMap(), this.facingRight, this));
				}
			}
		}
		
		
		// Entity collision
		for (int i=0;i < InGameLocal.projectiles.size();i++) {
			if (intersects(InGameLocal.projectiles.get(i)) && InGameLocal.projectiles.get(i).thisId != this) {
				hurt(InGameLocal.projectiles.get(i).getDamage());
				InGameLocal.projectiles.get(i).setHit();
			}
		}

		// Check for attack finish
		if (currentAction == MELEE_1H) {
			if(animationTorso.hasPlayedOnce()) hitting = false;
		}
		if (currentAction == SHOOTING_1H || currentAction == SHOOTING_2H) {
			if(isDead() || ((ItemTypeWeapon) heldItem).getAnimation().hasPlayedOnce()) {
				firing = false;
			}
		}
		
		// set animation
		if(firing) {
			if(currentAction != SHOOTING_1H && currentAction != SHOOTING_2H) {
				ItemTypeWeapon held = (ItemTypeWeapon) heldItem;
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
		
		animation.update();
		animationTorso.update();
			
			if(right) facingRight = true;
			if(left) facingRight = false;
			
	}
	public void hurt(int dmg) {
		if(flinching || isInvincible() || noclip || flying) return;
		super.hurt(dmg);
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	public void heal(int hp) {
		health = health + hp;
		if(health > 125) {
			health = 125;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	public void setHealth(int hp) {
		health = hp;
	}
	public void stop() {
		setLeft(false);
		setRight(false);
		setJumping(false);
	}
	public void drawPlayer(int x, int y, Graphics2D g) {
//		heldItem = new ItemPistol();
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
				ItemTypeWeapon held = (ItemTypeWeapon) heldItem;
				if(heldItem != null && heldItem.isValidWeapon() && held.getAnimation().getImage() != null) {
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
				ItemTypeWeapon held = (ItemTypeWeapon) heldItem;
				if(heldItem != null && heldItem.isValidWeapon()) {
					g.drawImage(held.getAnimation().getImage(), x-held.getSpriteX()+width, y+held.getSpriteY(), 
							-held.getAnimation().getImage().getWidth(), 
							held.getAnimation().getImage().getHeight(), null);
					held.getAnimation().update();
				}
			}
		}
	}
	public void draw(Graphics2D g) {
		setMapPosition();
//		heldItem = new ItemPistol();
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 50 % 2 == 0) {
				return;
			}
			if(elapsed > 125) {
				flinching = false;
			}
		}
		if(!dead) {
			drawPlayer((int)(x + xmap - width / 2), (int)(y + ymap - height / 2 - 8), g);
			drawName(g);
			g.setColor(PColor.BLACK);
			g.fillRect((int)(x + xmap - width / 2) + 68, (int)(y + ymap - height / 2 + 8), 104, 9);
			g.setColor(PColor.RED);
			g.fillRect((int)(x + xmap - width / 2) + 70, (int)(y + ymap - height / 2 + 10), 100, 5);
			g.setColor(PColor.GREEN);
			g.fillRect((int)(x + xmap - width / 2) + 70, (int)(y + ymap - height / 2 + 10), health, 5);
			if(health > 100) {
				g.setColor(PColor.BLACK);
				g.fillRect((int)(x + xmap - width / 2) + 170, (int)(y + ymap - height / 2 + 8), health-96, 9);
				g.setColor(PColor.BLUE);
				g.fillRect((int)(x + xmap - width / 2) + 172, (int)(y + ymap - height / 2 + 10), health-100, 5);
			}
		}

	}
	public void drawName(Graphics2D g) {
		if(heldItem != null) {
			int x = (int)(this.x + xmap - width / 2) + 20;
			int y = (int) (this.y + ymap - height / 2) - 20;
			g.setColor(PColor.GRAY);
			g.fillRect(x-2, y-2, 34, 34);
			g.setColor(PColor.DARK_GRAY);
			g.fillRect(x, y, 30, 30);
			g.drawImage(heldItem.getSprite(), x, y, 30, 30, null);
			if(heldItem.isValidWeapon()) {
				g.setFont(new Font("Arial", Font.BOLD, 13));
				guimaster.drawCenteredColoredOutlinedString(((ItemTypeWeapon) heldItem).getAmmo() + "/" + ((ItemTypeWeapon) heldItem).getMaxAmmo(), x + 20, y+35, PColor.WHITE, PColor.BLACK, 1, g);
			}
		}
	    g.setFont(new Font("Arial", Font.BOLD, 30));
		guimaster.drawCenteredColoredOutlinedString(name, (int)(x + xmap - width / 2) + width / 2, (int) (y + ymap - height / 2), PColor.WHITE, PColor.BLACK, 1, g);
	}
	public boolean isDead() {
		return dead;
	}
	public long getDeathTime() {
		return deadTime;
	}
}