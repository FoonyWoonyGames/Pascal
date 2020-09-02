package Customization;

import java.util.ArrayList;

public class Category {
	private String name;
	private ArrayList<ClothingItem> content;

	public Category(String n) {
		name = n;
		content = new ArrayList<ClothingItem>();
	}
	public String getName() {
		return name;
	}
	public ArrayList<ClothingItem> content() {
		return content;
	}
}
