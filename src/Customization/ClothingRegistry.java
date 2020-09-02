package Customization;

import java.util.ArrayList;

public class ClothingRegistry {
	public ArrayList<ClothingItem> hats;
	public ArrayList<ClothingItem> overcoats;
	public ArrayList<ClothingItem> tops;
	public ArrayList<ClothingItem> legs;
	public ArrayList<ClothingItem> feet;
	public ArrayList<ClothingItem> glasses;
	public ArrayList<ClothingItem> masks;
	public ArrayList<ClothingItem> sets;
	
	public ArrayList<Category> categoriesHat;
	public ArrayList<Category> categoriesOvercoat;
	public ArrayList<Category> categoriesTop;
	public ArrayList<Category> categoriesLegs;
	public ArrayList<Category> categoriesFeet;
	public ArrayList<Category> categoriesGlasses;
	public ArrayList<Category> categoriesMask;
	
	public static final int TYPE_HAT = 0;
	public static final int TYPE_OVERCOAT = 1;
	public static final int TYPE_TOP = 2;
	public static final int TYPE_LEGS = 3;
	public static final int TYPE_FEET = 4;
	public static final int TYPE_GLASSES = 5;
	public static final int TYPE_MASK = 6;
	public static final int TYPE_SET = 7;
	
	public ClothingRegistry() {
		hats = new ArrayList<ClothingItem>();
		overcoats = new ArrayList<ClothingItem>();
		tops = new ArrayList<ClothingItem>();
		legs = new ArrayList<ClothingItem>();
		feet = new ArrayList<ClothingItem>();
		glasses = new ArrayList<ClothingItem>();
		masks = new ArrayList<ClothingItem>();
		sets = new ArrayList<ClothingItem>();
		
		categoriesHat = new ArrayList<Category>();
		categoriesOvercoat = new ArrayList<Category>();
		categoriesTop = new ArrayList<Category>();
		categoriesLegs = new ArrayList<Category>();
		categoriesFeet = new ArrayList<Category>();
		categoriesGlasses = new ArrayList<Category>();
		categoriesMask = new ArrayList<Category>();
		
		addCategories();
		
		
		// NAME, SPRITE, ID, IN DATABASE, LOCKED BY DEFAULT, TYPE, CATEGORY
	}
	
	public void addItems() {
		addHats();
		addOvercoats();
		addTops();
		addLegs();
		addFeet();
		addGlasses();
		addMasks();
		addSets();
	}
	
	// HATS
	public void addHats() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_HAT, "null");
		
		new ClothingItem("White Cap", "hat.cap.capWhite", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Gray Cap", "hat.cap.capGray", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Black Cap", "hat.cap.capBlack", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Blue Cap", "hat.cap.capBlue", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Green Cap", "hat.cap.capGreen", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Yellow Cap", "hat.cap.capYellow", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Orange Cap", "hat.cap.capOrange", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Red Cap", "hat.cap.capRed", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Purple Cap", "hat.cap.capPurple", "hatCap", false, false, TYPE_HAT, "Caps");
		new ClothingItem("Christmas Hat", "hat.special.xmas", "hatXmas", true, true, TYPE_HAT, "Special");
	}
	
	// JACKETS
	public void addOvercoats() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_OVERCOAT, "null");
		
		new ClothingItem("White Hoodie", "overcoat.hoodie.hoodieWhite", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Gray Hoodie", "overcoat.hoodie.hoodieGray", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Black Hoodie", "overcoat.hoodie.hoodieBlack", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Blue Hoodie", "overcoat.hoodie.hoodieBlue", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Green Hoodie", "overcoat.hoodie.hoodieGreen", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Yellow Hoodie", "overcoat.hoodie.hoodieYellow", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Orange Hoodie", "overcoat.hoodie.hoodieOrange", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Red Hoodie", "overcoat.hoodie.hoodieRed", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Purple Hoodie", "overcoat.hoodie.hoodiePurple", "overcoatHoodie", false, false, TYPE_OVERCOAT, "Hoodies");
		
		new ClothingItem("Closed White Hoodie", "overcoat.hoodie.closedhoodieWhite", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Gray Hoodie", "overcoat.hoodie.closedhoodieGray", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Black Hoodie", "overcoat.hoodie.closedhoodieBlack", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Blue Hoodie", "overcoat.hoodie.closedhoodieBlue", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Green Hoodie", "overcoat.hoodie.closedhoodieGreen", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Yellow Hoodie", "overcoat.hoodie.closedhoodieYellow", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Orange Hoodie", "overcoat.hoodie.closedhoodieOrange", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Red Hoodie", "overcoat.hoodie.closedhoodieRed", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
		new ClothingItem("Closed Purple Hoodie", "overcoat.hoodie.closedhoodiePurple", "overcoatHoodieclosed", false, false, TYPE_OVERCOAT, "Hoodies");
	}
	
	// SHIRTS
	public void addTops() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_TOP, "null");
		
		new ClothingItem("White T-Shirt", "top.tshirt.tshirtWhite", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Gray T-Shirt", "top.tshirt.tshirtGray", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Black T-Shirt", "top.tshirt.tshirtBlack", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Blue T-Shirt", "top.tshirt.tshirtBlue", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Green T-Shirt", "top.tshirt.tshirtGreen", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Yellow T-Shirt", "top.tshirt.tshirtYellow", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Orange T-Shirt", "top.tshirt.tshirtOrange", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Red T-Shirt", "top.tshirt.tshirtRed", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		new ClothingItem("Purple T-Shirt", "top.tshirt.tshirtPurple", "topTshirt", false, false, TYPE_TOP, "T-Shirts");
		
		new ClothingItem("White Tanktop", "top.tanktop.tanktopWhite", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Gray Tanktop", "top.tanktop.tanktopGray", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Black Tanktop", "top.tanktop.tanktopBlack", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Blue Tanktop", "top.tanktop.tanktopBlue", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Green Tanktop", "top.tanktop.tanktopGreen", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Yellow Tanktop", "top.tanktop.tanktopYellow", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Orange Tanktop", "top.tanktop.tanktopOrange", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Red Tanktop", "top.tanktop.tanktopRed", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		new ClothingItem("Purple Tanktop", "top.tanktop.tanktopPurple", "topTanktop", false, false, TYPE_TOP, "Tanktops");
		
		new ClothingItem("White Shirt", "top.shirt.shirtWhite", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Gray Shirt", "top.shirt.shirtGray", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Black Shirt", "top.shirt.shirtBlack", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Blue Shirt", "top.shirt.shirtBlue", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Green Shirt", "top.shirt.shirtGreen", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Yellow Shirt", "top.shirt.shirtYellow", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Orange Shirt", "top.shirt.shirtOrange", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Red Shirt", "top.shirt.shirtRed", "topShirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Purple Shirt", "top.shirt.shirtPurple", "topShirt", false, false, TYPE_TOP, "Shirts");
		
		new ClothingItem("Tied White Shirt", "top.shirt.tiedshirtWhite", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Gray Shirt", "top.shirt.tiedshirtGray", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Black Shirt", "top.shirt.tiedshirtBlack", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Blue Shirt", "top.shirt.tiedshirtBlue", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Green Shirt", "top.shirt.tiedshirtGreen", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Yellow Shirt", "top.shirt.tiedshirtYellow", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Orange Shirt", "top.shirt.tiedshirtOrange", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Red Shirt", "top.shirt.tiedshirtRed", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
		new ClothingItem("Tied Purple Shirt", "top.shirt.tiedshirtPurple", "topTiedshirt", false, false, TYPE_TOP, "Shirts");
	}
	
	// PANTS
	public void addLegs() {
		new ClothingItem("Undies", "null", "Empty", false, false, TYPE_LEGS, "null");
		
		new ClothingItem("White Jeans", "legs.jeans.jeansWhite", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Gray Jeans", "legs.jeans.jeansGray", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Black Jeans", "legs.jeans.jeansBlack", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Blue Jeans", "legs.jeans.jeansBlue", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Green Jeans", "legs.jeans.jeansGreen", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Yellow Jeans", "legs.jeans.jeansYellow", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Orange Jeans", "legs.jeans.jeansOrange", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Red Jeans", "legs.jeans.jeansRed", "legsJeans", false, false, TYPE_LEGS, "Jeans");
		new ClothingItem("Purple Jeans", "legs.jeans.jeansPurple", "legsJeans", false, false, TYPE_LEGS, "Jeans");
	}
	
	// SHOES
	public void addFeet() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_FEET, "null");
		
		new ClothingItem("White Shoes", "feet.shoes.shoesWhite", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Gray Shoes", "feet.shoes.shoesGray", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Black Shoes", "feet.shoes.shoesBlack", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Blue Shoes", "feet.shoes.shoesBlue", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Green Shoes", "feet.shoes.shoesGreen", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Yellow Shoes", "feet.shoes.shoesYellow", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Orange Shoes", "feet.shoes.shoesOrange", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Red Shoes", "feet.shoes.shoesRed", "feetShoes", false, false, TYPE_FEET, "Shoes");
		new ClothingItem("Purple Shoes", "feet.shoes.shoesPurple", "feetShoes", false, false, TYPE_FEET, "Shoes");
	}
	
	// EYEWEAR
	public void addGlasses() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_GLASSES, "null");
		
		new ClothingItem("White Regular Glasses", "glasses.regular.regularWhite", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Gray Regular Glasses", "glasses.regular.regularGray", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Black Regular Glasses", "glasses.regular.regularBlack", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Blue Regular Glasses", "glasses.regular.regularBlue", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Green Regular Glasses", "glasses.regular.regularGreen", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Yellow Regular Glasses", "glasses.regular.regularYellow", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Orange Regular Glasses", "glasses.regular.regularOrange", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Red Regular Glasses", "glasses.regular.regularRed", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		new ClothingItem("Purple Regular Glasses", "glasses.regular.regularPurple", "glassesRegular", false, false, TYPE_GLASSES, "Regular");
		
		new ClothingItem("White Casual Sunglasses", "glasses.casuals.casualsWhite", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Gray Casual Sunglasses", "glasses.casuals.casualsGray", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Black Casual Sunglasses", "glasses.casuals.casualsBlack", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Blue Casual Sunglasses", "glasses.casuals.casualsBlue", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Green Casual Sunglasses", "glasses.casuals.casualsGreen", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Yellow Casual Sunglasses", "glasses.casuals.casualsYellow", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Orange Casual Sunglasses", "glasses.casuals.casualsOrange", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Red Casual Sunglasses", "glasses.casuals.casualsRed", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");
		new ClothingItem("Purple Casual Sunglasses", "glasses.casuals.casualsPurple", "glassesCasuals", false, false, TYPE_GLASSES, "Casual Sunglasses");

		new ClothingItem("White Goggles", "glasses.goggles.gogglesWhite", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Gray Goggles", "glasses.goggles.gogglesGray", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Black Goggles", "glasses.goggles.gogglesBlack", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Blue Goggles", "glasses.goggles.gogglesBlue", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Green Goggles", "glasses.goggles.gogglesGreen", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Yellow Goggles", "glasses.goggles.gogglesYellow", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Orange Goggles", "glasses.goggles.gogglesOrange", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Red Goggles", "glasses.goggles.gogglesRed", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");
		new ClothingItem("Purple Goggles", "glasses.goggles.gogglesPurple", "glassesGoggles", false, false, TYPE_GLASSES, "Goggles");

		new ClothingItem("White Blindfold", "glasses.blindfold.blindfoldWhite", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Gray Blindfold", "glasses.blindfold.blindfoldGray", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Black Blindfold", "glasses.blindfold.blindfoldBlack", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Blue Blindfold", "glasses.blindfold.blindfoldBlue", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Green Blindfold", "glasses.blindfold.blindfoldGreen", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Yellow Blindfold", "glasses.blindfold.blindfoldYellow", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Orange Blindfold", "glasses.blindfold.blindfoldOrange", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Red Blindfold", "glasses.blindfold.blindfoldRed", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");
		new ClothingItem("Purple Blindfold", "glasses.blindfold.blindfoldPurple", "glassesBlindfold", false, false, TYPE_GLASSES, "Blindfolds");

		new ClothingItem("Heart Glasses", "glasses.special.valentine", "glassesValentine", false, false, TYPE_GLASSES, "Special");
	}
	
	// MASKS
	public void addMasks() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_MASK, "null");
		
		new ClothingItem("White Facemask", "mask.facemask.facemaskWhite", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Gray Facemask", "mask.facemask.facemaskGray", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Black Facemask", "mask.facemask.facemaskBlack", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Blue Facemask", "mask.facemask.facemaskBlue", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Green Facemask", "mask.facemask.facemaskGreen", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Yellow Facemask", "mask.facemask.facemaskYellow", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Orange Facemask", "mask.facemask.facemaskOrange", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Red Facemask", "mask.facemask.facemaskRed", "maskFace", false, false, TYPE_MASK, "Facemasks");
		new ClothingItem("Purple Facemask", "mask.facemask.facemaskPurple", "maskFace", false, false, TYPE_MASK, "Facemasks");
	}
	
	// SETS
	public void addSets() {
		new ClothingItem("---", "null", "Empty", false, false, TYPE_SET, "null");

		new ClothingItem("Robe of Administration", "set.robeAdministration", "setRobeAdministration", true, true, TYPE_SET, "null");
		new ClothingItem("Robe of Moderation", "set.robeModeration", "setRobeModeration", true, true, TYPE_SET, "null");
		new ClothingItem("Robe of Testing", "set.robeTesting", "setRobeTesting", true, true, TYPE_SET, "null");
		
	}
	public void addCategories() {
		categoriesHat.add(new Category("Caps"));
		categoriesHat.add(new Category("Special"));

		categoriesOvercoat.add(new Category("Hoodies"));

		categoriesTop.add(new Category("T-Shirts"));
		categoriesTop.add(new Category("Tanktops"));
		categoriesTop.add(new Category("Shirts"));

		categoriesLegs.add(new Category("Jeans"));
		
		categoriesFeet.add(new Category("Shoes"));
		
		categoriesGlasses.add(new Category("Regular"));
		categoriesGlasses.add(new Category("Casual Sunglasses"));
		categoriesGlasses.add(new Category("Goggles"));
		categoriesGlasses.add(new Category("Blindfolds"));
		categoriesGlasses.add(new Category("Special"));
		
		categoriesMask.add(new Category("Facemasks"));
	}
	public Category getCategory(int type, String name) {
		Category c = null;
		if(type == TYPE_HAT) {
			for (int i=0;i < categoriesHat.size();i++) {
				if(categoriesHat.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesHat.get(i); 
					break;
				}
			}
		}
		else if(type == TYPE_OVERCOAT) {
			for (int i=0;i < categoriesOvercoat.size();i++) {
				if(categoriesOvercoat.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesOvercoat.get(i);
					break;
				}
			}
		}
		if(type == TYPE_TOP) {
			for (int i=0;i < categoriesTop.size();i++) {
				if(categoriesTop.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesTop.get(i);
					break;
				}
			}
		}
		if(type == TYPE_LEGS) {
			for (int i=0;i < categoriesLegs.size();i++) {
				if(categoriesLegs.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesLegs.get(i);
					break;
				}
			}
		}
		if(type == TYPE_FEET) {
			for (int i=0;i < categoriesFeet.size();i++) {
				if(categoriesFeet.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesFeet.get(i);
					break;
				}
			}
		}
		if(type == TYPE_GLASSES) {
			for (int i=0;i < categoriesGlasses.size();i++) {
				if(categoriesGlasses.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesGlasses.get(i);
					break;
				}
			}
		}
		if(type == TYPE_MASK) {
			for (int i=0;i < categoriesMask.size();i++) {
				if(categoriesMask.get(i).getName().equalsIgnoreCase(name)) {
					c = categoriesMask.get(i);
					break;
				}
			}
		}
		return c;
	}
	public int getItem(int type, String name) {
		int c = 0;
		if(type == TYPE_HAT) {
			for (int i=0;i < hats.size();i++) {
				if(hats.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		else if(type == TYPE_OVERCOAT) {
			for (int i=0;i < overcoats.size();i++) {
				if(overcoats.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		if(type == TYPE_TOP) {
			for (int i=0;i < tops.size();i++) {
				if(tops.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		if(type == TYPE_LEGS) {
			for (int i=0;i < legs.size();i++) {
				if(legs.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		if(type == TYPE_FEET) {
			for (int i=0;i < feet.size();i++) {
				if(feet.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		if(type == TYPE_GLASSES) {
			for (int i=0;i < glasses.size();i++) {
				if(glasses.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		if(type == TYPE_MASK) {
			for (int i=0;i < masks.size();i++) {
				if(masks.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		if(type == TYPE_SET) {
			for (int i=0;i < sets.size();i++) {
				if(sets.get(i).getName().equalsIgnoreCase(name)) {
					c = i;
					break;
				}
			}
		}
		return c;
	}
}
