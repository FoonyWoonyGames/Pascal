package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemBread extends ItemTypeConsumable {
	
	public ItemBread() {
		super();
		
		nameUnlocalized = "item.bread";
		nameLocalized = "Bread";
		id = 11;
		idText = "bread";
		sound = "item.bread";
		variation = "01";
		rank = NORMAL;
		description = "Restores health.\nIt's not warm anymore.";
		cooldown = 5000;
		
		type = 0;
		points = 12;
	
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
