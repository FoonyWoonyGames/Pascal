package Container;

import java.awt.Graphics2D;
import java.util.ArrayList;

import GUI.GuiMaster;
import Item.Item;

public abstract class Inventory {
	protected int maxItems;
	public ArrayList<Slot> slots;
	protected ArrayList<Item> items;
	private boolean open;

	protected GuiMaster guimaster;
	public Inventory() {
		slots = new ArrayList<Slot>();
		items = new ArrayList<Item>();
		guimaster = new GuiMaster();
	}
	public void addItem(Item i) {
		if(maxItems == 0 || items.size() <= maxItems) {
			items.add(i);
		}
	}
	public void removeItem(Item i) {
		if(items.contains(i)) {
			items.remove(i);
		}
		else {
			System.err.println(i.getUnlocalizedName() + " is not in inventory.");
		}
	}
	public boolean isOpen() {
		return open;
	}
	public void openInventory(boolean b) {
		open = b;
	}
	public int length() {
		return items.size();
	}
	public boolean contains(Item i) {
		return items.contains(i);
	}
	public boolean contains(int item) {
		boolean b = false;
		loopCheck:
		for (int i=0;i < items.size();i++) {
			if(items.get(i).getID() == item) {
				b = true;
				break loopCheck;
			}
		}
		return b;
	}
	public Item get(int l) {
		return items.get(l);
	}
	public boolean isFull() {
		return items.size() >= maxItems;
	}
	public void update() {
		for (int i=0;i < items.size();i++) {
			if(items.get(i).getID() == 0) {
				items.remove(items.get(i));
			}
		}
	}
	public abstract void draw(Graphics2D g);
}
