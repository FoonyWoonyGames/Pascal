package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemChoke extends ItemTypeConsumable {
	
	public ItemChoke() {
		super();
		
		nameUnlocalized = "item.choke";
		nameLocalized = "Can of Choke";
		id = 14;
		idText = "can";
		sound = "item.can";
		variation = "01";
		rank = NORMAL;
		description = "Restores energy.\n\"Refreshing as heck\"";
		cooldown = 5000;
		
		type = 1;
		points = 500;
	
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
