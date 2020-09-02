package Entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import AI.Ai;
import AI.AiAggressiveStill;
import AI.AiAggressiveWandering;
import AI.AiPassiveStill;
import AI.AiPassiveWandering;
import Item.Item;
import GUI.GuiMaster;
import GUI.GuiTooltip;
import GameState.InGame;
import Pascal.GamePanel;
import TileMap.TileMap;
import Util.PColor;
import Util.Sound;

public abstract class Npc extends Mob {
	public static ArrayList<Npc> Npcs = new ArrayList<Npc>();
	
	protected ArrayList<BufferedImage[]> sprites;
	protected final int[] numFrames = {
			2, 2, 1, 1
	};
	
	protected static final int IDLE = 0;
	protected static final int WALKING = 1;
	protected static final int JUMPING = 2;
	protected static final int FALLING = 3;
	protected static final int ATTACKING = 4;
	public boolean standingStill;

	protected static GuiMaster guimaster = new GuiMaster();
	
	protected Sound soundPlayer;
	
	protected String imageid = "default";
	
	protected String nameShort = "Default";
	protected String nameLong = "Default NPC";
	
	protected boolean dead;
	protected boolean shouldDrop;
	protected boolean flinching;
	protected long flinchTimer;
	
	protected int state;
	protected int startX;
	protected int startY;
	
	protected long attackRate;
	protected long lastAttack;
	protected int attackType;
	protected Ai ai;
	protected int eyeRadius;
	protected int damage;
	protected int attackrange;
	protected boolean attacking;
	protected ArrayList<Item> drops;
	protected ArrayList<Ai> AIs;

	public static final int ATTACKTYPE_MELEE = 0;
	public static final int ATTACKTYPE_RANGED = 1;
	
	public static final int AIPASSIVESTILL = 0;
	public static final int AIPASSIVEWANDERING = 1;
	public static final int AIAGGRESSIVESTILL = 2;
	public static final int AIAGGRESSIVEWANDERING = 3;
	
	public Npc(TileMap tm) {
		super(tm);
		entities.add(this);
		addNpc();
		drops = new ArrayList<Item>();

		facingRight = true;
		moveSpeed = 0.5;
		maxSpeed = 2.8;
		stopSpeed = 0.6;
		
		fallSpeed = 0.3;
		maxFallSpeed = 12.0;
		jumpStart = -6.4;
		stopJumpSpeed = 0.6;
		
		startX = getx();
		startY = gety();
		
		soundPlayer = new Sound();
		AIs = new ArrayList<Ai>();
		AIs.add(new AiPassiveStill(this));
		AIs.add(new AiPassiveWandering(this));
		AIs.add(new AiAggressiveStill(this));
		AIs.add(new AiAggressiveWandering(this));
		
		health = healthMax = 100;
		damage = 0;
		attackType = ATTACKTYPE_MELEE;
		attackRate = 1000;
	}
	public Item getDrop(int id) {
		Item item = null;
		for (int i=0;i < drops.size();i++) {
			if (drops.get(i).getID() == id) {
				item = drops.get(i);
			}
		}
		return item;
	}
	public void reset() {
		health = healthMax;
		revive();
		setPosition(startX, startY);
		state = 0;
	}
	public void addDrop(Item i) {
		drops.add(i);
	}
	public void drop(Item item) {
		item.drop(getx(), gety(), getTileMap());
		drops.remove(item);
	}
	public void give(Item item) {
		GamePanel.currentPlayer.itemAdd(item);
		drops.remove(item);
	}
	public BufferedImage getFace() {
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/faces/" + imageid + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;
	}
	public void use() {
		
	}
	public int getEyeRadius() {
		return eyeRadius;
	}
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartPos(int x, int y) {
		startX = x;
		startY = y;
		setPosition(x, y);
	}
	public void hurt(int dmg) {
		if(isInvincible() || dead) return;
		super.hurt(dmg);
		if(isEssential() && health < 1) {
			health = 1;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	public void setIdle() {
		setLeft(false);
		setRight(false);
		if(currentAction != IDLE) {
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(200);
			width = 240;
			standingStill = true;
		}
	}
	
	private void getNextPosition() {
		
		// movement
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
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;	
		}
		
		// falling
		if(falling) {
			
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
		if(stillX) {
			dx = 0;
		}
		if(stillY) {
			dy = 0;
		}
		
	}
	public void update() {
		if(!dead) {
			if (getx() > 0 && gety() > 0 && getx() < getTileMap().getWidth()-120 && gety() < getTileMap().getHeight() - 120) {
				getNextPosition();
				checkTileMapCollision();
				setPosition(xtemp, ytemp);
			}
			ai.update();

			if (getx() < 0 || gety() < 0 || getx() > getTileMap().getWidth()-120 || gety() > getTileMap().getHeight() - 120) {
				if(!essential && !invincible) {
					killNoDrop();
				}
				else if(essential || invincible) {
					setPosition(startX, startY);
				}
			}
			

			// AUTOJUMP
			if(currentAction == WALKING && xdest != x) {
				setJumping(true);
			}
			else {
//				setJumping(false);
			}
			if(ai.getTarget() != null) {
				if(ai.getTarget().getx() < getx() + getAttackRange() && ai.getTarget().getx() > getx() - getAttackRange() && isAttacking() &&
						ai.getTarget().gety() < gety() + getAttackRange() && ai.getTarget().gety() > gety() - getAttackRange()) {
					commenceAttack();
				}
			}

//			if(!isDead()) {
//				for(int j = 0; j < InGame.player.fires.size(); j++) {
//					if(InGame.player.fires.get(j).intersects(this) && !this.isDead() && InGame.player.fires.get(j).thisId != this) {
//						hurt(InGame.player.fires.get(j).getDamage());
//						InGame.player.fires.get(j).setHit();
//					}
//				}
//			}
			
			// Death
			if(health <= 0) {
				health = 0;
				dead = true;
				onDeath();
				if(!shouldDrop) {
					shouldDrop = true;
				}
				for (int i=0;i < drops.size();i++) {
					Item item = drops.get(i);
					drop(item);
				}
			}
			if(dy > 0) {
				if(currentAction != FALLING) {
					currentAction = FALLING;
					animation.setFrames(sprites.get(FALLING));
					animation.setDelay(-1);
					width = 240;
					standingStill = false;
				}
			}
			else if(dy < 0) {
				if(currentAction != JUMPING) {
					currentAction = JUMPING;
					animation.setFrames(sprites.get(JUMPING));
					animation.setDelay(-1);
					width = 240;
					standingStill = false;
				}
		}
			else if(left || right) {
				if(currentAction != WALKING) {
					currentAction = WALKING;
					animation.setFrames(sprites.get(WALKING));
					animation.setDelay(100);
					width = 240;
					standingStill = false;
				}
			}
			else {
				if(currentAction != IDLE) {
					currentAction = IDLE;
					animation.setFrames(sprites.get(IDLE));
					animation.setDelay(200);
					width = 240;
					standingStill = true;
				}
			}
			
			animation.update();
				
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
			if(elapsed > 200) {
				flinching = false;
			}
		}
		
		if(flinching || !dead) {
			if(GamePanel.mouseX > (int)(x + xmap - width / 2) &&
					GamePanel.mouseX < (int)(x + xmap - width / 2) + width &&
					GamePanel.mouseY > (int)(y + ymap - height / 2) &&
					GamePanel.mouseY < (int)(y + ymap - height / 2) + height) {
				g.setFont(new Font("Arial", Font.BOLD, 25));
				guimaster.drawCenteredColoredOutlinedString(getLongName(), 
						(int)(x + xmap - width / 2) + width/2, (int)(y + ymap - height / 2), 
						PColor.WHITE, PColor.BLACK, 1, g);
			}
			if(facingRight) {
				g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2),
					(int)(y + ymap - height / 2-8),
					null
				);
			}
			else {
				g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2 + width),
					(int)(y + ymap - height / 2-8),
					-width,
					height,
					null
				);
			}
		}
	}
	
	public String getShortName() {
		return nameShort;
	}
	public String getLongName() {
		return nameLong;
	}
	public void setAi(int ai) {
		this.ai = AIs.get(ai);
	}
	public Ai getAi() {
		return ai;
	}
	public boolean isDead() {
		return dead;
	}
	public int getHealth() {
		return health;
	}
	public void kill() {
		this.setInvincible(false);
		this.setEssential(false);
		hurt(getHealth());
	}
	public void killNoDrop() {
		for (int i=0;i < drops.size();i++) {
			drops.remove(drops.get(i));
		}
		kill();
	}
	public void revive() {
		if(dead) {
			health = healthMax;
			dead = false;
		}
	}
	public void addNpc() {
		Npc.Npcs.add(this);
	}
	public int getAttackRange() {
		return attackrange;
	}
	public void setAttackRange(int i) {
		attackrange = i;
	}
	public void setAttacking(boolean b) {
		attacking = b;
	}
	public boolean isAttacking() {
		return attacking;
	}
	public void setDamage(int d) {
		damage = d;
	}
	public int getDamage() {
		return damage;
	}
	public void commenceAttack() {
		if(System.currentTimeMillis() - lastAttack > attackRate) {
			attack();
			lastAttack = System.currentTimeMillis();
		}
	}
	public abstract void attack();
	public void setState(int i) {
		state = i;
	}
	public int getState() {
		return state;
	}
	public void setHealth(int h) {
		health = h;
	}
	public void onDeath() {
		
	}
}
