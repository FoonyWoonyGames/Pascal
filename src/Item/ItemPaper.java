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

public class ItemPaper extends Item {
	
	public ItemPaper() {
		super();
		
		nameUnlocalized = "item.paper";
		nameLocalized = "Paper";
		id = 16;
		idText = "paper";
		sound = "item.paper";
		variation = "01";
		rank = USELESS;
		description = "It's a blank piece of paper.";
		cooldown = 0;
		
		
		weight = 0.5;
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
