package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemChokeLight extends ItemTypeConsumable {
	
	public ItemChokeLight() {
		super();
		
		nameUnlocalized = "item.choke";
		nameLocalized = "Can of Diet Choke";
		id = 18;
		idText = "can";
		sound = "item.can";
		variation = "02";
		rank = NORMAL;
		description = "Restores energy.\n\"Pretty refreshing...\"";
		cooldown = 5000;
		
		type = 1;
		points = 250;
	
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
