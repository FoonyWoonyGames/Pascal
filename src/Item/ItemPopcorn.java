package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemPopcorn extends ItemTypeConsumable {
	
	public ItemPopcorn() {
		super();
		
		nameUnlocalized = "item.popcorn";
		nameLocalized = "Popcorn";
		id = 9;
		idText = "popcorn";
		sound = "item.bread";
		variation = "01";
		rank = NORMAL;
		description = "Sold by the popcorn vendor\nin Heimdurnk.";
		cooldown = 5000;
		price = 5;
		
		type = 0;
		points = 8;
	
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
