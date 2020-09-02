package Util;

import java.util.ArrayList;

import GUI.GuiVendor;
import Item.Item;
import Item.ItemApple;
import Item.ItemBread;
import Item.ItemBreadMoldy;
import Item.ItemCakePiece;
import Item.ItemChoke;
import Item.ItemChokeFake;
import Item.ItemChokeLight;
import Item.ItemHealth;
import Item.ItemKey;
import Item.ItemMagicDamage;
import Item.ItemPaper;
import Item.ItemPaperWritten;
import Item.ItemPistol;
import Item.ItemPopcorn;
import Item.ItemRock;
import Item.ItemRocketBoots;
import Item.ItemShotgun;
import Item.ItemStick;
import Item.ItemSunblade;
import Pascal.Game;

public class ItemRegistry {
	public GuiVendor vendor;
	public ArrayList<Item> Registry = new ArrayList<Item>();
	public ItemRegistry() {
		RegisterItem(new ItemHealth());				// 1
		RegisterItem(new ItemKey());				// 2
		RegisterItem(new ItemPistol());				// 3
		RegisterItem(new ItemSunblade());			// 4
		RegisterItem(new ItemRocketBoots());		// 5
		RegisterItem(new ItemShotgun());			// 6
		RegisterItem(new ItemMagicDamage());		// 7
		RegisterItem(new ItemCakePiece());			// 8
		RegisterItem(new ItemPopcorn());			// 9
		RegisterItem(new ItemRock());				// 10
		RegisterItem(new ItemBread());				// 11
		RegisterItem(new ItemBreadMoldy());			// 12
		RegisterItem(new ItemApple());				// 13
		RegisterItem(new ItemChoke());				// 14
		RegisterItem(new ItemStick());				// 15
		RegisterItem(new ItemPaper());				// 16
		RegisterItem(new ItemPaperWritten());		// 17
		RegisterItem(new ItemChokeLight());			// 18
		RegisterItem(new ItemChokeFake());			// 19
	}
	public GuiVendor getVendor() {
		vendor = new GuiVendor();
		vendor.setTitle("Item-Registry");
		for (int i=0;i < Registry.size();i++) {
			if(i != 3) {
				vendor.addItem(Registry.get(i));
			}
		}
		return vendor;
	}
	public void RegisterItem(Item item) {
		Registry.add(item);
	}
	
	public Item getItem(int id) {
		Game.itemRegistry = new ItemRegistry();
		Item item = null;
		for (int i=0;i < Registry.size();i++) {
			if(Registry.get(i).getID() == id) {
				item = Registry.get(i);
			}
		}
		return item;
	}
	public Item getItem(String id) {
		Item item = null;
		for (int i=0;i < Registry.size();i++) {
			if(Registry.get(i).getTextID().equals(id+"")) {
				item = Registry.get(i);
			}
		}
		return item;
	}
}
