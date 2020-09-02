package Entity;

import TileMap.TileMap;

public class Mob extends MapObject {
	protected int health;
	protected int healthMax;
	
	public Mob(TileMap tm) {
		super(tm);
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return healthMax;
	}
	public void hurt(int dmg) {
		health = health - dmg;
	}
	public void heal(int dmg) {
		health = health + dmg;
		if(health > healthMax) {
			health = healthMax;
		}
	}
	public void turnAround() {
		if(facingRight) {
			facingRight = false;
		} else {
			facingRight = true;
		}
	}
	public void turnAround(boolean b) {
		facingRight = b;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
