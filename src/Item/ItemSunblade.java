package Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import Item.Item;
import Pascal.GamePanel;

public class ItemSunblade extends Item {
	
	public static boolean held;
	public ItemSunblade() {
		
		super();

		nameUnlocalized = "item.sunblade";
		nameLocalized = "Blade of the Four Suns";
		id = 1337;
		idText="sunblade";
		variation = "";
		sound="item.weapon.bladeLong.pickup";
		rank = LEGENDARY;
		description = "Constantly heals you\nDamages for 500 hp.";

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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUse() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEquip() {
		File f = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/bladeofthefoursuns.fwg");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/bladeofthefoursuns.fwg"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		String textinfile = null;
		try {
			textinfile = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String requestedtext = "538475837593485743295723840143810570468597349857243981276398145678457648567485724852";
		if(!f.exists() || !textinfile.equals(requestedtext)) {
			GamePanel.currentPlayer.inventory.removeItem(this);
		}
	}
	public static void checkFor() {
		File f = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/bladeofthefoursuns.fwg");
		if(f.exists()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/bladeofthefoursuns.fwg"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			String textinfile = null;
			try {
				textinfile = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String requestedtext = "538475837593485743295723840143810570468597349857243981276398145678457648567485724852";
			if(textinfile.equals(requestedtext) && !GamePanel.currentPlayer.inventory.contains(1337)) {
				GamePanel.currentPlayer.inventory.addItem(new ItemSunblade());
			}
		}
	}
	@Override
	public void onHold() {
		GamePanel.currentPlayer.setHealth(GamePanel.currentPlayer.getHealth() + 2);
	}
}
