package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Account.User;
import Customization.Category;
import GUI.GuiButton;
import GUI.GuiMaster;
import GUI.GuiScrollable;
import Pascal.Game;
import Settings.Controls;
import Storage.CharacterManager;
import Util.PAction;
import Util.PColor;
import Util.Sound;

public class CharacterCustomization extends GameState {

	private static GuiMaster guim = new GuiMaster();

	private static GuiButton buttonAccept = new GuiButton("ccdone");
	private static GuiButton buttonBack = new GuiButton("ccback");
	private static GuiButton buttonNext = new GuiButton("ccnext");
	private static GuiButton buttonPrev = new GuiButton("ccprev");
	private static GuiButton buttonHat = new GuiButton("cchat");
	private static GuiButton buttonOvercoat = new GuiButton("ccovercoat");
	private static GuiButton buttonTop = new GuiButton("cctop");
	private static GuiButton buttonLegs = new GuiButton("cclegs");
	private static GuiButton buttonFeet = new GuiButton("ccfeet");
	private static GuiButton buttonGlasses = new GuiButton("ccglasses");
	private static GuiButton buttonMask = new GuiButton("ccmask");
	private static GuiButton buttonSet = new GuiButton("ccset");

	private static GuiButton buttonHatNone = new GuiButton("cchatnone");
	private static GuiButton buttonOvercoatNone = new GuiButton("ccovercoatnone");
	private static GuiButton buttonTopNone = new GuiButton("cctopnone");
	private static GuiButton buttonLegsNone = new GuiButton("cclegsnone");
	private static GuiButton buttonFeetNone = new GuiButton("ccfeetnone");
	private static GuiButton buttonGlassesNone = new GuiButton("ccglassesnone");
	private static GuiButton buttonMaskNone = new GuiButton("ccmasknone");
	private static GuiButton buttonSetNone = new GuiButton("ccsetnone");
    
	public static int chosenHat = 0;
	public static int chosenOvercoat = 0;
	public static int chosenTop = 1;
	public static int chosenLegs = 4;
	public static int chosenFeet = 2;
	public static int chosenGlasses = 0;
	public static int chosenMask = 0;
	public static int chosenSet = 0;
	public static BufferedImage preview;
	public static BufferedImage Body;
	public static BufferedImage Hat;
	public static BufferedImage Overcoat;
	public static BufferedImage Top;
	public static BufferedImage Legs;
	public static BufferedImage Feet;
	public static BufferedImage Glasses;
	public static BufferedImage Mask;
	public static BufferedImage Set;
	
	public static ArrayList<GuiButton> buttonsCategories;
	public static ArrayList<GuiButton> buttonsItems = new ArrayList<GuiButton>();
	
	public static int page;
	public static int pagesMax;
	public static Category currentCategory;
	public static int state;
	
	public static final int STATE_HAT = 0;
	public static final int STATE_OVERCOAT = 1;
	public static final int STATE_TOP = 2;
	public static final int STATE_LEGS = 3;
	public static final int STATE_FEET = 4;
	public static final int STATE_GLASSES = 5;
	public static final int STATE_MASK = 6;
	public static final int STATE_SET = 7;
	public static final int STATE_MAIN = 10;

	BufferedImage hat;
	BufferedImage overcoat;
	BufferedImage top;
	BufferedImage legs;
	BufferedImage feet;
	BufferedImage glasses;
	BufferedImage mask;
	BufferedImage set;
	
	private static GuiScrollable scroll;
	
	public CharacterCustomization(GameStateManager gsm) {
		this.gsm = gsm;
		File f = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/character.txt");
		if(f.exists()) {
			User.loadCustomizations(Game.username);
//			CharacterManager.Load();
		}
		try {
			scroll = new GuiScrollable(900, 500, 900, 500);
	        Body = ImageIO.read(getClass().getResourceAsStream("/textures/sprites/player/player.png"));
	        
	        buttonHat.setType(GuiButton.TYPE_ICON);
	        buttonOvercoat.setType(GuiButton.TYPE_ICON);
	        buttonTop.setType(GuiButton.TYPE_ICON);
	        buttonLegs.setType(GuiButton.TYPE_ICON);
	        buttonFeet.setType(GuiButton.TYPE_ICON);
	        buttonGlasses.setType(GuiButton.TYPE_ICON);
	        buttonMask.setType(GuiButton.TYPE_ICON);
	        buttonSet.setType(GuiButton.TYPE_ICONLARGE);
	        
	        buttonHatNone.setTitle("No Hat");
	        buttonOvercoatNone.setTitle("No Jacket");
	        buttonTopNone.setTitle("No Top");
	        buttonLegsNone.setTitle("No Pants");
	        buttonFeetNone.setTitle("No Shoes");
	        buttonGlassesNone.setTitle("No Eyewear");
	        buttonMaskNone.setTitle("No Mask");
	        buttonSetNone.setTitle("No Set");

		    buttonAccept.setTitle("Done");
		    buttonAccept.setType(GuiButton.TYPE_BOXHALF);
		    buttonAccept.setUse(new PAction() {
				@Override
				public void command() {
					GameStateManager.setState(GameStateManager.MENUSTATE);
					setParts();
					User.saveCustomizations(Game.username, chosenHat, chosenOvercoat, chosenTop, chosenLegs, chosenFeet, chosenGlasses, chosenMask, chosenSet);
				}
		    });
		    buttonBack.setTitle("Back");
		    buttonBack.setType(GuiButton.TYPE_BOXHALF);
		    buttonNext.setType(GuiButton.TYPE_ARROWRIGHT);
		    buttonPrev.setType(GuiButton.TYPE_ARROWLEFT);

	        hat = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/hat.png"));
	        overcoat = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/overcoat.png"));
	        top = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/top.png"));
	        legs = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/legs.png"));
	        feet = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/feet.png"));
	        glasses = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/glasses.png"));
	        mask = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/mask.png"));
	        set = ImageIO.read(getClass().getResourceAsStream("/textures/gui/customization/set.png"));
	        
			setParts();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void init() {
		page = 0;
		state = 10;
		currentCategory = null;
	}
	public void update() {
		if(currentCategory == null && state != STATE_MAIN && state != STATE_SET) {
			pagesMax = (buttonsCategories.size()/5);
		}
		else {
			pagesMax = (buttonsItems.size()/5);
		}
		if(page == 0) {
			buttonPrev.setDisabled(true);
		}
		else {
			buttonPrev.setDisabled(false);
		}
		if(page == pagesMax) {
			buttonNext.setDisabled(true);
		}
		else {
			buttonNext.setDisabled(false);
		}
	}
	public static void setLocked(String str) {
		str = "(Locked)" + str;
	}
	public static void setUnlocked(String str) {
		str = str.replace("(Locked)", "");
	}
	public static boolean isLocked(String str) {
		return str.contains("(Locked");
	}
	public void setParts() {
		try {
			Hat = Game.clothingRegistry.hats.get(chosenHat).getSprite();
			Overcoat = Game.clothingRegistry.overcoats.get(chosenOvercoat).getSprite();
			Top = Game.clothingRegistry.tops.get(chosenTop).getSprite();
			Legs = Game.clothingRegistry.legs.get(chosenLegs).getSprite();
			Feet = Game.clothingRegistry.feet.get(chosenFeet).getSprite();
			Glasses = Game.clothingRegistry.glasses.get(chosenGlasses).getSprite();
			Mask = Game.clothingRegistry.masks.get(chosenMask).getSprite();
			Set = Game.clothingRegistry.sets.get(chosenSet).getSprite();
			
	    	preview = getPreview();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		GradientPaint gradient = new GradientPaint(0, 0, PColor.DARK_BLUE, 0, 1300, PColor.LIGHT_BLUE);
		g.setPaint(gradient);
		g.fillRect(0, 0, 1920, 1080);
	    g.drawImage(preview, -50, 250, null);
	    if(state == STATE_MAIN) {
		    buttonHat.draw(800, 300, g);
		    buttonOvercoat.draw(960, 300, g);
		    buttonTop.draw(1120, 300, g);
		    buttonLegs.draw(800, 460, g);
		    buttonFeet.draw(960, 460, g);
		    buttonGlasses.draw(1120, 460, g);
		    buttonMask.draw(800, 620, g);
		    buttonSet.draw(1040, 620, g);
		    g.drawImage(hat, 725, 300, null);
		    g.drawImage(overcoat, 880, 300, null);
		    g.drawImage(top, 1040, 300, null);
		    g.drawImage(legs, 725, 450, null);
		    g.drawImage(feet, 885, 460, null);
		    g.drawImage(glasses, 1040, 460, null);
		    g.drawImage(mask, 725, 620, null);
		    g.drawImage(set, 885, 620, null);
		    
		    g.setFont(new Font("Arial", Font.BOLD, 70));
		    if(buttonHat.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("HATS", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonOvercoat.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("JACKETS", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonTop.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("SHIRTS", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonLegs.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("PANTS", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonFeet.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("SHOES", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonGlasses.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("EYEWEAR", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonMask.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("MASKS", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
		    else if(buttonSet.isHovered()) {
		    	guim.drawCenteredColoredOutlinedString("SETS", 960, 200, PColor.WHITE, PColor.BLACK, 1, g);
		    }
	    }
	    else if(state == STATE_HAT) {
		    g.drawImage(hat, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("HATS", 280, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonHatNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_OVERCOAT) {
		    g.drawImage(overcoat, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("JACKETS", 340, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonOvercoatNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_TOP) {
		    g.drawImage(top, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("TOPS", 300, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonTopNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_LEGS) {
		    g.drawImage(legs, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("PANTS", 320, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonLegsNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_FEET) {
		    g.drawImage(feet, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("SHOES", 320, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonFeetNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_GLASSES) {
		    g.drawImage(glasses, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("EYEWEAR", 380, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonGlassesNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_MASK) {
		    g.drawImage(mask, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("MASKS", 320, 250, PColor.WHITE, PColor.BLACK, 1, g);
	    	if(currentCategory == null) {
		    	buttonMaskNone.draw(960, 200, g);
	    	}
	    }
	    else if(state == STATE_SET) {
		    g.drawImage(set, 50, 150, null);
		    g.setFont(new Font("Arial", Font.BOLD, 70));
	    	guim.drawCenteredColoredOutlinedString("SETS", 460, 250, PColor.WHITE, PColor.BLACK, 1, g);
		    buttonSetNone.draw(960, 200, g);
	    }
	    if(state != STATE_MAIN && state != STATE_SET) {
		    buttonBack.draw(960, 880, g);
	    	if(currentCategory == null) {
//				for (int i= 0 + (page * 5);i < (page*5 + 5);i++) {
//					if(i < buttonsCategories.size()) {
//						buttonsCategories.get(i).draw(960, 300 + ((i-page*5) * 100), g);
//					}
//				}
		    	if(buttonsCategories.size() > 5) {
			    	scroll.setMaxHeight((buttonsCategories.size())*100);
		    	} else {
			    	scroll.setMaxHeight(500);
		    	}
				for (int i= 0;i < buttonsCategories.size();i++) {
					buttonsCategories.get(i).setScrollable(scroll);
					buttonsCategories.get(i).draw(500, i * 100, scroll.getGraphics());
				}
				scroll.draw(460, 300, g);
	    	}
	    	else {
			    g.setFont(new Font("Arial", Font.BOLD, 40));
		    	guim.drawCenteredColoredOutlinedString(currentCategory.getName().toUpperCase(), 960, 250, PColor.WHITE, PColor.BLACK, 1, g);
//				for (int i= 0 + (page * 5);i < (page*5 + 5);i++) {
//					if(i < buttonsItems.size()) {
//						buttonsItems.get(i).draw(960, 300 + ((i-page*5) * 100), g);
//					}
//				}
		    	if(buttonsItems.size() > 5) {
			    	scroll.setMaxHeight((buttonsItems.size())*100);
		    	} else {
			    	scroll.setMaxHeight(500);
		    	}
				for (int i= 0;i < buttonsItems.size();i++) {
					buttonsItems.get(i).setScrollable(scroll);
					buttonsItems.get(i).draw(500, i * 100, scroll.getGraphics());
				}
				scroll.draw(460, 300, g);
	    	}
//		    buttonNext.draw(1650, 900, g);
//		    buttonPrev.draw(1500, 900, g);
//		    g.setFont(new Font("Arial", Font.BOLD, 40));
//		    guim.drawCenteredColoredOutlinedString("PAGE", 1530, 930, PColor.WHITE, PColor.BLACK, 1, g);
//		    guim.drawCenteredColoredOutlinedString((page+1) + "/" + (pagesMax+1), 1530, 970, PColor.WHITE, PColor.BLACK, 1, g);
	    }
	    if(state == STATE_SET) {
			for (int i= 0 + (page * 5);i < (page*5 + 5);i++) {
				if(i < buttonsItems.size()) {
					buttonsItems.get(i).draw(960, 300 + ((i-page*5) * 100), g);
				}
			}
	    }

	    buttonAccept.draw(960, 980, g);
	    
	}


	public BufferedImage getPreview() {
        int w = 600;
        int h = 600;
        BufferedImage finalImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();

	    g.drawImage(Body.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		if(chosenSet == 0) {
		    if(Feet != null) {
			    g.drawImage(Feet.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		    if(Glasses != null) {
			    g.drawImage(Glasses.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		    if(Mask != null) {
		    	g.drawImage(Mask.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		    if(Legs != null) {
			    g.drawImage(Legs.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		    if(Top != null) {
			    g.drawImage(Top.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		    if(Overcoat != null) {
			    g.drawImage(Overcoat.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		    if(Hat != null) {
		    	g.drawImage(Hat.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
		    }
		}
		else {
			if(Set != null) {
				g.drawImage(Set.getSubimage(0, 0, 240, 240), 0, 0, 600, 600, null);
			}
		}
        g.dispose();
        return finalImage;
	}


	public void keyPressed(int k, char c) {
		Controls.pressGlobal(k);
		if (k == KeyEvent.VK_BACK_SPACE) {
			GameStateManager.setState(GameStateManager.MENUSTATE);
			Sound soundPlayer = new Sound();
			soundPlayer.play("gui.select");
			soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundInterface))));
		}
		if(k == KeyEvent.VK_ESCAPE) {
			GameStateManager.setState(GameStateManager.MENUSTATE);
			Sound soundPlayer = new Sound();
			soundPlayer.play("gui.select");
			soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundInterface))));
		}
	}

	@Override
	public void keyReleased(int k) {
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		Controls.mousereleaseGlobal(m);
	}
	public static void resetScrollbar() {
		scroll.setYProcent(0);
	}
	

	
}

//if(currentHatColor(blue) = hatColor[2](liste)) {
//	hatColor = new Color(0, 0, 255);
//}
//if(CharacterState.currentHatColor == CharacterState.hatColor[9]) {
//	joinImage(img7, hat_txfd
// img7.colorize(CharacterState.hatColor);