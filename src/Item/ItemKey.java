package Item;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.InGame;
import Item.Item;

public class ItemKey extends Item {
	
	private String keyId;
	public static ArrayList<ItemKey> keys = new ArrayList<ItemKey>();
	public ItemKey() {
		super();
		
		nameUnlocalized = "item.key";
		nameLocalized = "Key";
		id = 2;
		idText = "key";
		sound = "item.key";
		variation = "01";
		rank = NORMAL;
		description = "Used to unlock things.";
		
		keyId = "00";
		validKey = true;
		keys.add(this);
	

		weight = 1;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + variation + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public String getKeyID() {
		return keyId;
	}
	public void setKeyID(String str) {
		keyId = str;
	}
	public void setKeyID(String str1, String str2) {
		if(nameLocalized.equalsIgnoreCase(str1)) {
			keyId = str2;
		}
	}
	
	// Make sure the key has the right ID -- Unused since 2,Key,[ID] (SaveManager)
	public void checkID() {
		setKeyID("Basement Key", "basementExit");
	}

	@Override
	public void onPickup() {
		if(keyId.equalsIgnoreCase("basementExit")) {
			if(InGame.objm.noObjective()) {
				InGame.objm.addObjective("0101");
				InGame.objm.getObjective("0101").nextState();
			}
			else if(InGame.objm.containsObjective("0101") && InGame.objm.getObjective("0101").getState() == 0) {
				InGame.objm.getObjective("0101").nextState();
			}
		}
	}

	@Override
	public void onUse() {
		// TODO Auto-generated method stub
		
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
