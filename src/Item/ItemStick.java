package Item;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import Entity.ProjectileThrown;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;
import Projectile.ProjectilePistol;
import TileMap.TileMap;

public class ItemStick extends Item {
	
	public ItemStick() {
		super();
		
		nameUnlocalized = "item.stick";
		nameLocalized = "Stick";
		id = 15;
		idText = "stick";
		sound = "item.wood";
		variation = "01";
		rank = USELESS;
		description = "Looks like it's from a tree.";
		cooldown = 0;
		
		
		weight = 1.0;
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
