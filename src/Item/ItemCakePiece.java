package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemCakePiece extends ItemTypeConsumable {
	
	public ItemCakePiece() {
		super();
		
		nameUnlocalized = "item.cake";
		nameLocalized = "Piece of Cake";
		id = 8;
		idText = "cakePiece";
		sound = "item.bread";
		variation = "01";
		rank = NORMAL;
		description = "There's even a cherry on\nthe top.";
		cooldown = 5000;
		
		type = 0;
		points = 15;
	
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
