package Item;

import GameState.InGame;
import Pascal.Cheats;

public class ItemTypeConsumable extends Item {

	protected int type;
	protected int points;
	
	public static final int TYPE_HEALTH = 0;
	public static final int TYPE_ENERGY = 1;
	public ItemTypeConsumable() {
		validFood = true;
		rank = NORMAL;
		type = 0;
		points = 1;
	}
	public int getFoodType() {
		return type;
	}
	public int getFoodPoints() {
		return points;
	}
	@Override
	public void onPickup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUse() {
		InGame.player.inventory.removeItem(this);
		if(type == TYPE_HEALTH) {
			InGame.player.heal(points);
			InGame.player.calculateHealthImage();
		}
		else if(type == TYPE_ENERGY) {
			InGame.player.setEnergy(InGame.player.getEnergy() + points);
		}
	}

	@Override
	public void onEquip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHold() {
		// TODO Auto-generated method stub
		
	}

}
