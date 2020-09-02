package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemBreadMoldy extends ItemTypeConsumable {
	
	public ItemBreadMoldy() {
		super();
		
		nameUnlocalized = "item.bread";
		nameLocalized = "Moldy Bread";
		id = 12;
		idText = "bread";
		sound = "item.bread";
		variation = "02";
		rank = NORMAL;
		description = "Restores health.\nIt's a little moldy but..\nit should be fine!";
		cooldown = 5000;
		
		type = 0;
		points = 6;
	
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
