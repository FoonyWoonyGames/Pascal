package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemApple extends ItemTypeConsumable {
	
	public ItemApple() {
		super();
		
		nameUnlocalized = "item.apple";
		nameLocalized = "Apple";
		id = 13;
		idText = "apple";
		sound = "item.bread";
		variation = "01";
		rank = NORMAL;
		description = "Restores health.\nThere's a bit of dirt on it.";
		cooldown = 5000;
		
		type = 0;
		points = 9;
	
		weight = 0.9;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + variation + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onPickup() {
	}

	@Override
	public void onUse() {
		super.onUse();
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
