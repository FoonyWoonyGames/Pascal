package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemChokeFake extends ItemTypeConsumable {
	
	public ItemChokeFake() {
		super();
		
		nameUnlocalized = "item.choke";
		nameLocalized = "Can of Chokk";
		id = 19;
		idText = "can";
		sound = "item.can";
		variation = "03";
		rank = NORMAL;
		description = "That's disappointing.";
		cooldown = 5000;
		
		type = 1;
		points = 50;
	
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
