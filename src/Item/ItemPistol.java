package Item;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import GameState.InGame;
import Pascal.Game;
import Pascal.GamePanel;
import Projectile.ProjectilePistol;
import TileMap.TileMap;

public class ItemPistol extends ItemTypeWeapon {

	public ItemPistol() {
		
		super();
		
		nameUnlocalized = "item.pistol";
		nameLocalized = "Pistol";
		id = 3;
		idText="pistol";
		variation = "01";
		sound="item.weapon.pistol.pickup";
		rank = NORMAL;
		description = "Used to fire shots.\nDo not operate while drunk.";
		cooldown = 600;
		ammo = ammoMax = 12;
		reloadtime = 1200;
		shots = 1;
		
		weight = 1.2;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + variation + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace(); 
		}
		x = 180;
		y = 90;
		setSpritesheet("pistol", 60, 40);
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(120);
		
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
				soundPlayer.play("item.weapon.pistol.fire");
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
		super.onEquip();
	}
	@Override
	public void onHold() {
		super.onHold();
	}
	public Projectile getProjectile(TileMap tm, boolean facingRight, MapObject mo) {
		Projectile a = new ProjectilePistol(tm, facingRight, mo);
		a.setPosition(mo.getx(), mo.gety());
		return a;
	}
	public void reload() {
		if(ammo < ammoMax && !reloading) {
			soundPlayer.play("item.weapon.pistol.reload");
			soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundItems))));
			super.reload();
		}
	}
}
