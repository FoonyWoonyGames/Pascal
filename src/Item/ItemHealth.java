package Item;

import javax.imageio.ImageIO;

import Item.Item;
import Pascal.GamePanel;

public class ItemHealth extends Item {
	
	public ItemHealth() {
		super();

		nameUnlocalized = "item.health";
		nameLocalized = "100HP";
		id = 1;
		idText="health";
		rank = LEGENDARY;
		description = "Heals you for 100 HP.\nAlpha exclusive!";
		cooldown = 120000;
		
		weight = 1;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + ".png"));
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
		GamePanel.currentPlayer.heal(100);
	}


	@Override
	public void onEquip() {
		
	}


	@Override
	public void onHold() {
		
	}
}
