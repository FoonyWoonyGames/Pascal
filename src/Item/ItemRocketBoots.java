package Item;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;

public class ItemRocketBoots extends Item {
	
	private String keyId;
	public ItemRocketBoots() {
		super();
		
		nameUnlocalized = "item.rocketboots";
		nameLocalized = "Rocket Boots";
		id = 5;
		idText = "rocketboots";
		sound = "event.bagOpen";
		variation = "01";
		rank = GOOD;
		description = "Produced by QBL.\nShoot for the moon.";
		cooldown = 3000;
	
		weight = 1;
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
		InGame.player.push(MapObject.DIRECTION_UP, 50);
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
