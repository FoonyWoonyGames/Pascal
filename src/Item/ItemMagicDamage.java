package Item;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import GameState.InGame;
import Pascal.Game;
import Pascal.GamePanel;
import Projectile.ProjectileMagic;
import TileMap.TileMap;

public class ItemMagicDamage extends ItemTypeWeapon {

	public ItemMagicDamage() {
		
		super();
		
		nameUnlocalized = "item.magicDamage";
		nameLocalized = "Tome of Damage";
		id = 7;
		idText="magic";
		variation = "01";
		sound="item.paper";
		rank = LEGENDARY;
		description = "Used by the ancient wizards.";
		cooldown = 400;
		ammo = ammoMax = 0;
		reloadtime = 1200;
		shots = 1;
		
		weight = 1.2;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + variation + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace(); 
		}
		x = 160;
		y = 92;
		setSpritesheet("magic", 60, 45);
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(150);
		
	}
	@Override
	public void onPickup() {
	}
	@Override
	public void onUse() {
		if(!reloading) {
			fixAnimation();
			InGame.player.setFiring();
			soundPlayer.play("item.weapon.magic.fire");
			soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
		}
	}
	@Override
	public void onEquip() {
	}
	@Override
	public void onHold() {
		super.onHold();
	}
	public Projectile getProjectile(TileMap tm, boolean facingRight, MapObject mo) {
		Projectile a = new ProjectileMagic(tm, facingRight, mo, "Damage");
		a.setPosition(mo.getx(), mo.gety());
		return a;
	}
	public void reload() {
	}
}
