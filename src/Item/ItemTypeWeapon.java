package Item;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import GameState.InGame;
import Pascal.GamePanel;
import TileMap.TileMap;

public class ItemTypeWeapon extends Item {
	public int ammo;
	public int ammoMax;
	public long reloadtime;
	private long lastReload;
	protected boolean reloading;
	protected int shots;
	protected Projectile projectileType;
	protected boolean twohanded;
	protected int x;
	protected int y;
	
	protected Animation animation;
	protected ArrayList<BufferedImage[]> sprites;
	protected BufferedImage sprite;
	private final int[] numFrames = {
			2
	};
	public ItemTypeWeapon() {
		validWeapon = true;
		reloadtime = 1000;
		reloading = false;
		shots = 1;
		twohanded = false;
		animation = new Animation();
	}
	public void setSpritesheet(String str, int w, int h) {
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/item/" + str + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace(); 
		}

		sprites = new ArrayList<BufferedImage[]>();

		for(int i = 0; i < 1; i++) {
			BufferedImage[] bi = new BufferedImage[numFrames[i]];
			for(int j = 0; j < numFrames[i]; j++) {
				bi[j] = sprite.getSubimage(j * w, i * h, w, h);
			}
			sprites.add(bi);
		}
	}
	public int getSpriteX() {
		return x;
		
	}
	public int getSpriteY() {
		return y;
	}
	public int getAmmo() {
		return ammo;
	}
	public int getMaxAmmo() {
		return ammoMax;
	}
	public boolean isTwoHanded() {
		return twohanded;
	}
	public Projectile getProjectile(TileMap tm, boolean facingRight, MapObject mo) {
		Projectile a = new Projectile(tm, facingRight, mo);
		a.setPosition(mo.getx(), mo.gety());
		return a;
	}
	public void reload() {
		if(ammo < ammoMax && !reloading) {
			lastReload = System.currentTimeMillis();
			reloading = true;
		}
	}
	public long getReloadTime() {
		return reloadtime;
	}
	public int getShots() {
		return shots;
	}
	@Override
	public void onPickup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUse() {
		if(ammo > 0) {
			ammo--;
		}
	}
	public void fixAnimation() {
		animation.setFrames(sprites.get(0));
		animation.setFrame(0);
	}

	@Override
	public void onEquip() {
	}

	private boolean zoom = false;
	@Override
	public void onHold() {
		if(reloading) {
			if(System.currentTimeMillis()-lastReload > getReloadTime()) {
				reloading = false;
				ammo = ammoMax;
			}
		}
		if(zoom) {
			if(InGame.player.isRight()) {
				InGame.tileMap.setPosition(GamePanel.WIDTH / 2 - InGame.player.getx() - 2000, GamePanel.HEIGHT / 2 - InGame.player.gety());
			}
			else {
				InGame.tileMap.setPosition(GamePanel.WIDTH / 2 - InGame.player.getx() + 2000, GamePanel.HEIGHT / 2 - InGame.player.gety());
			}
		}
	}
	public void zoom(boolean b) {
//		zoom = b;
//		InGame.tileMap.freeze(b);
	}

	public Animation getAnimation() {
		return animation;
	}

}
