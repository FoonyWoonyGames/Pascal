package Item;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import GameState.InGame;
import Pascal.Game;
import Pascal.GamePanel;
import Projectile.ProjectileShotgun;
import TileMap.TileMap;

public class ItemShotgun extends ItemTypeWeapon {

	public ItemShotgun() {
		
		super();
		
		nameUnlocalized = "item.shotgun";
		nameLocalized = "Shotgun";
		id = 6;
		idText="shotgun";
		variation = "01";
		sound="item.weapon.shotgun.pickup";
		rank = NORMAL;
		description = "Used to fire shots.\nDon't point at children.";
		cooldown = 900;
		ammo = ammoMax = 6;
		reloadtime = 1500;
		shots = 4;
		twohanded = true;
		
		weight = 1.5;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + variation + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace(); 
		}
		setSpritesheet("shotgun", 200, 160);
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
			if(ammo > 0) {
				fixAnimation();
				InGame.player.setFiring();
				soundPlayer.play("item.weapon.shotgun.fire");
				soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
			}
			else {
				soundPlayer.play("gui.select");
				soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
			}
			super.onUse();
		}
	}
	@Override
	public void onEquip() {
		
	}
	@Override
	public void onHold() {
		super.onHold();
		x = 90;
		y = 40;
	}
	public Projectile getProjectile(TileMap tm, boolean facingRight, MapObject mo) {
		Projectile a = new ProjectileShotgun(tm, facingRight, mo);
		a.setPosition(mo.getx(), mo.gety());
		return a;
	}
	public void reload() {
		if(ammo < ammoMax && !reloading) {
			soundPlayer.play("item.weapon.shotgun.reload" + (6-getAmmo()));
			soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
			reloadtime = 450*(6-getAmmo()) + 950;
			super.reload();
		}
	}
}
