package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Customization.Category;
import Customization.ClothingItem;
import Customization.ClothingRegistry;
import Entity.Npc;
import GameState.CharacterCustomization;
import GameState.Error;
import GameState.GameStateManager;
import GameState.InGame;
import GameState.InGameLocal;
import GameState.Menu;
import HUD.HudSplash;
import Pascal.Game;
import Pascal.GamePanel;
import Storage.CharacterManager;
import Storage.SaveManager;
import Util.Crash;
import Util.PAction;
import Util.PColor;
import Util.Sound;
import Zone.Zone;
import net.GameClient;

public class GuiButton extends GuiComponent {
	public boolean hovered;
	private boolean disabled = false;
	public int gx = 0;
	public int gy = 0;
	
	public int ex = 0;
	public int ey = 0;
	public GuiScrollable scrollbar;
	
	public String buttonTitle = "null";
	private int type;
//	private String type = "button";
	private static long lastclicked;
	private Sound soundPlayer;
	
	private BufferedImage b1;
	private BufferedImage b2;
	private BufferedImage b3;
	private Color customColor;
	
	public static final int TYPE_BOXFULL = 0;
	public static final int TYPE_BOXHALF = 1;
	public static final int TYPE_BOXSMALL = 2;
	public static final int TYPE_ARROWRIGHT = 3;
	public static final int TYPE_ARROWLEFT = 4;
	public static final int TYPE_CROSS = 5;
	public static final int TYPE_INVISIBLE = 6;
	public static final int TYPE_ICON = 7;
	public static final int TYPE_ICONLARGE = 8;
	
	
	public GuiButton(String str) {
		super(str);
		GamePanel.buttons.add(this);
		setType(0);
		soundPlayer = new Sound();
	}
	public void setType(int t) {
		type = t;
		if (t == 0) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/normal/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/normal/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/normal/buttonDisabled.png"));
		    } catch (IOException e) {}
		}
		if (t == 1) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/half/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/half/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/half/buttonDisabled.png"));
		    } catch (IOException e) {}
			
		}
		if (t == 2) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/short/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/short/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/short/buttonDisabled.png"));
		    } catch (IOException e) {}
			
		}
		if(type == 3 || type == 4) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/arrow/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/arrow/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/arrow/buttonDisabled.png"));
		    } catch (IOException e) {}
		}
		if(t == 5) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/close/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/close/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/close/buttonDisabled.png"));
		    } catch (IOException e) {}
		}
		if(t == 6) {
			b1 = null;
			b2 = null;
			b3 = null;
		}
		if(t == 7) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/icon/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/icon/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/icon/buttonDisabled.png"));
		    } catch (IOException e) {}
		}
		if(t == 8) {
		    try {
		        b1 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/iconlarge/button.png"));
		        b2 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/iconlarge/buttonHovered.png"));
		        b3 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/button/iconlarge/buttonDisabled.png"));
		    } catch (IOException e) {}
		}
	}
	public void setTitle(String title) {
		buttonTitle = title;
	}
	public String getTitle() {
		return buttonTitle;
	}
	public void setDisabled(boolean onoff) {
		disabled = onoff;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public int getX() {
		return gx;
	}
	public int getY() {
		return gy;
	}
	public String getID() {
		return id;
	}
	public void update() {
		if (isVisible()) {
			if (!isDisabled()) {
				if(scrollbar != null) {
					if(GamePanel.mouseY >= scrollbar.getLocationY() && GamePanel.mouseY <= scrollbar.getHeight() + scrollbar.getLocationY()) {
						if(type != 4) {
							if (GamePanel.mouseX > gx && GamePanel.mouseX < gx + b1.getWidth() && GamePanel.mouseY > gy && GamePanel.mouseY < gy + b1.getHeight()) {
								setHovered(true);
							}
							else {
								setHovered(false);
							}
						}
						else {
							if (GamePanel.mouseX < gx && GamePanel.mouseX > gx - b1.getWidth() && GamePanel.mouseY > gy && GamePanel.mouseY < gy + b1.getHeight()) {
								setHovered(true);
							}
							else {
								setHovered(false);
							}
						}
					} else {
						setHovered(false);
					}
				} else {
					if(type != 4) {
						if (GamePanel.mouseX > gx && GamePanel.mouseX < gx + b1.getWidth() && GamePanel.mouseY > gy && GamePanel.mouseY < gy + b1.getHeight()) {
							setHovered(true);
						}
						else {
							setHovered(false);
						}
					}
					else {
						if (GamePanel.mouseX < gx && GamePanel.mouseX > gx - b1.getWidth() && GamePanel.mouseY > gy && GamePanel.mouseY < gy + b1.getHeight()) {
							setHovered(true);
						}
						else {
							setHovered(false);
						}
					}
				}
			}
		}
	}
	public void draw(int x, int y, Graphics2D g) {
		x = x - (b1.getWidth()/2);
		gx = x + ex;
		gy = y + ey;

		if (isVisible()) {
			if (!isDisabled()) {
				update();
				if(isHovered()) {
					for (int i=0;i < GamePanel.buttons.size();i++) {
						if(GamePanel.buttons.get(i) != this) {
							GamePanel.buttons.get(i).setHovered(false);
						}
					}
					if(type == 4) {
						g.drawImage(b2, x, y, -b1.getWidth(), b1.getHeight(), null);
					}
					else {
						g.drawImage(b2, x, y, null);
					}
					if(type != 3 && type != 4 && type != 5 && type != 7 && type != 8) {
						g.setFont(new Font("Arial", Font.BOLD, 35));
						if(customColor == null) {
							drawCenteredColoredOutlinedString(getTitle(), x + b1.getWidth() / 2, y + b1.getHeight() / 2 + 12, PColor.LIGHT_YELLOW, PColor.BLACK, 1, g);
						}
						else {
							drawCenteredColoredOutlinedString(getTitle(), x + b1.getWidth() / 2, y + b1.getHeight() / 2 + 12, customColor, PColor.BLACK, 1, g);
						}
					}
				}
				else {
					if(type == 4) {
						g.drawImage(b1, x, y, -b1.getWidth(), b1.getHeight(), null);
					}
					else {
						g.drawImage(b1, x, y, null);
					}
					if(type != 3 && type != 4 && type != 5 && type != 7 && type != 8) {
						g.setFont(new Font("Arial", Font.BOLD, 35));
						if(customColor == null) {
							drawCenteredColoredOutlinedString(getTitle(), x + b1.getWidth() / 2, y + b1.getHeight() / 2 + 12, PColor.WHITE, PColor.BLACK, 1, g);
						}
						else {
							drawCenteredColoredOutlinedString(getTitle(), x + b1.getWidth() / 2, y + b1.getHeight() / 2 + 12, customColor, PColor.BLACK, 1, g);
						}
					}
				}
			}
			if (isDisabled()) {
				if(type == 4) {
					g.drawImage(b3, x, y, -b1.getWidth(), b1.getHeight(), null);
				}
				else {
					g.drawImage(b3, x, y, null);
				}
				if(type != 3 && type != 4 && type != 5) {
					g.setFont(new Font("Arial", Font.BOLD, 35));
					if(customColor == null) {
						drawCenteredColoredOutlinedString(getTitle(), x + b1.getWidth() / 2, y + b1.getHeight() / 2 + 12, PColor.LIGHT_GREY, PColor.BLACK, 1, g);
					}
					else {
						drawCenteredColoredOutlinedString(getTitle(), x + b1.getWidth() / 2, y + b1.getHeight() / 2 + 12, customColor, PColor.BLACK, 1, g);
					}
				}
			}
			update();
		}
	}
	public boolean isHovered() {
		return hovered;
	}
	public void setHovered(boolean bol) {
		hovered = bol;
	}
	public void setColor(Color c) {
		customColor = c;
	}
	public void setScrollable(GuiScrollable s) {
		ex = s.getLocationX();
		ey = s.getLocationY() - s.getY();
		scrollbar = s;
	}
	// If you pressed settings in options 
	static boolean settings;
	// Click events
	public void press() {
		if(isHovered() && System.currentTimeMillis() - lastclicked > 400 && isVisible()) {
			use();
			lastclicked = System.currentTimeMillis();
			if(Game.settings.clicksounds) {
				soundPlayer.play("gui.select");
				soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundInterface))));
			}
			// Error - Go to menu
			if(getID().equalsIgnoreCase("errorback")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
			}
			if(getID().equalsIgnoreCase("errordetails")) {
				Error.Explain = true;
			}
			
			// Main menu
			if(getID().equalsIgnoreCase("singleplayer")) { // Singleplayer
				Menu.setState(Menu.STATE_SINGLEPLAYER);
			}
			if(getID().equalsIgnoreCase("multiplayer")) { // Multiplayer
				Menu.setState(Menu.STATE_MULTIPLAYER);
			}
			if(getID().equalsIgnoreCase("settings")) { // Settings
				Menu.setState(Menu.STATE_SETTINGS);
			}
			if(getID().equalsIgnoreCase("quit")) { // Quit game
				System.exit(0);
			}
			
			// Character customization menu
			pressCC();
			
			// Settings menu
			if(getID().equalsIgnoreCase("backtosettings")) {
				Menu.setState(Menu.STATE_SETTINGS);
			}
			if(getID().equalsIgnoreCase("graphics")) {
				Menu.setState(Menu.STATE_GRAPHICS);
			}
			if(getID().equalsIgnoreCase("audio")) {
				Menu.setState(Menu.STATE_AUDIO);
			}
			if(getID().equalsIgnoreCase("advanced")) {
				Menu.setState(Menu.STATE_ADVANCED);
			}
			if(getID().equalsIgnoreCase("social")) {
				Menu.setState(Menu.STATE_SOCIAL);
			}
			if(getID().equalsIgnoreCase("restore")) {
				Game.settings.restore();
			}
			if(getID().equalsIgnoreCase("customchar")) {
				GameStateManager.setState(GameStateManager.CHARACTERSTATE);
			}
			if(getID().equalsIgnoreCase("settingsback")) {
				try {
					GameStateManager.setState(GameStateManager.MENUSTATE);
				}
				catch (Exception e) {
					Crash.printReport(e);
				}
			} 
			if(getID().equalsIgnoreCase("shownicknames")) {
				Game.settings.toggle("shownicknames");
			}
			
			// Graphics menu
			if(getID().equalsIgnoreCase("antialias")) {
				Game.settings.toggle("antialias");
			}
			if(getID().equalsIgnoreCase("shadows")) {
				Game.settings.toggle("shadows");
			}
			
			// Pause menu
			if(getID().equalsIgnoreCase("pauseresume")) {
				GameStateManager.setPaused(false);
			}
			if(getID().equalsIgnoreCase("pauseoptions")) {
				GuiPause.setState(GuiPause.STATE_OPTIONS);
			}
			if(getID().equalsIgnoreCase("exittomenu")) {
				GuiPause.setState(GuiPause.STATE_CONFIRMEXIT);
			}
			if(getID().equalsIgnoreCase("forceexit")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
			}
			
			// Options menu
			if(getID().equalsIgnoreCase("optionsback")) {
				GuiPause.setState(GuiPause.STATE_MAIN);
			}
			if(getID().equalsIgnoreCase("optionsobjectives")) {
				GuiPause.setState(GuiPause.STATE_OBJECTIVES);
			}
			if(getID().equalsIgnoreCase("optionssettings")) {
				GuiPause.setState(GuiPause.STATE_CONFIRMEXIT);
				settings = true;
			}
			if(getID().equalsIgnoreCase("optionssave")) {
				SaveManager.Save(GamePanel.currentPlayer, 1);
				GuiPause.setState(GuiPause.STATE_MAIN);
				new HudSplash("Saved", PColor.TIP);
			}
			if(getID().equalsIgnoreCase("optionsload")) {
				try {
					SaveManager.Load(GamePanel.currentPlayer);
				} catch (NumberFormatException e) {
					GameStateManager.setState(GameStateManager.MENUSTATE);
					GuiFrame frame;
					frame = new GuiFrame("saveerror");
					frame.setTitle("Corrupt savefile");
					frame.setText("It would seem that your savefile\nis broken. Try deleting it, or overwriting it.\nIf that doesn't work, please do report\nthis bug and send the savefile so it can be fixed!");
					frame.setWidth(800);
					frame.setHeight(260);
					frame.setPosition(960, 260);
					frame.setVisible(true);
					Menu.displayFrame(frame);
				}
			}
			
			// Confirm exit menu
			if(getID().equalsIgnoreCase("exitYes")) {
				SaveManager.Save(GamePanel.currentPlayer, 1);
				new HudSplash("Saved", PColor.TIP);
				GameStateManager.setState(GameStateManager.MENUSTATE);
				GuiPause.setState(GuiPause.STATE_MAIN);
				if(settings) {
					Menu.setState(Menu.STATE_SETTINGS);
				}
				settings = false;
			}
			if(getID().equalsIgnoreCase("exitNo")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
				GuiPause.setState(GuiPause.STATE_MAIN);
				if(settings) {
					Menu.setState(Menu.STATE_SETTINGS);
				}
				settings = false;
			}
			if(getID().equalsIgnoreCase("exitCancel")) {
				GuiPause.setState(GuiPause.STATE_MAIN);
				settings = false;
			}
			
			// Dead menu
			if(getID().equalsIgnoreCase("respawn")) {
				File f = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves/save1.fwgs");
				if(f.exists() && !f.isDirectory()) { 
					SaveManager.Load(GamePanel.currentPlayer);
				}
				else {
					GameStateManager.setState(GameStateManager.currentState);
					GameStateManager.setDeath(false);
				}
			}
			if(getID().equalsIgnoreCase("death_exittomenu")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
				GameStateManager.setDeath(false);
			}
			
			
			// Singleplayer menu
			if(getID().equalsIgnoreCase("gamenew")) {
				GameStateManager.setState(GameStateManager.INGAMESTATE);
				InGame.setZone(Zone.EVIGILANTHILLS01);
				for (int i=0;i < Npc.Npcs.size();i++) {
					Npc.Npcs.get(i).reset();
				}
			}
			if(getID().equalsIgnoreCase("gameload")) {
				try {
					SaveManager.Load(GamePanel.currentPlayer);
				} catch (NumberFormatException e) {
					GameStateManager.setState(GameStateManager.MENUSTATE);
					GuiFrame frame;
					frame = new GuiFrame("saveerror");
					frame.setTitle("Corrupt savefile");
					frame.setText("It would seem that your savefile\nis broken. Try deleting it, or overwriting it.\nIf that doesn't work, please do report\nthis bug and send the savefile so it can be fixed!");
					frame.setWidth(800);
					frame.setHeight(260);
					frame.setPosition(960, 260);
					frame.setVisible(true);
					Menu.displayFrame(frame);
				}
			}
			if(getID().equalsIgnoreCase("masterback")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
			}
			
			// Multiplayer menu
			if(getID().equalsIgnoreCase("local")) {
				 Menu.setState(Menu.STATE_LOCAL);
			}
			if(getID().equalsIgnoreCase("online")) {
				Menu.setState(Menu.STATE_ONLINE);
			}
			if(getID().equalsIgnoreCase("mpback")) {
				Menu.setState(Menu.STATE_MAIN);
			}
			
			// Local menu
			if(getID().equalsIgnoreCase("local_freeroaming")) {
				GuiLocal.selectedMode = GuiLocal.MODE_FREEROAM;
			}
			if(getID().equalsIgnoreCase("local_deathmatch")) {
				GuiLocal.selectedMode = GuiLocal.MODE_DEATHMATCH;
			}
			if(getID().equalsIgnoreCase("local_enter")) {
				GameStateManager.setState(GameStateManager.INGAMELOCALSTATE);
				((InGameLocal) GameStateManager.gameStates.get(GameStateManager.INGAMELOCALSTATE)).setMap(GuiLocal.selectedMap);
			}
			
			// Online menu
			if(getID().equalsIgnoreCase("mpjoin")) {
				GuiOnline.joining = "true";
				GameClient.connect();
			}
			if(getID().equalsIgnoreCase("onlineback")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
				Menu.setState(Menu.STATE_MULTIPLAYER);
			}
			
			// Play online menu
			if(getID().equalsIgnoreCase("ponlineback")) {
				GameStateManager.setState(GameStateManager.MENUSTATE);
			}
		}
		setHovered(false);
	}
	public void pressCC() {
		CharacterCustomization.resetScrollbar();
//		for (int i=0;i < CharacterCustomization.buttonsCategories.size();i++) {
//			GamePanel.buttons.remove(CharacterCustomization.buttonsCategories.get(i));
//		}
//		for (int i=0;i < CharacterCustomization.buttonsItems.size();i++) {
//			GamePanel.buttons.remove(CharacterCustomization.buttonsItems.get(i));
//		}
		if(getID().equalsIgnoreCase("ccdone")) {
//			GameStateManager.setState(GameStateManager.MENUSTATE);
//			((CharacterCustomization) GameStateManager.gameStates.get(GameStateManager.CHARACTERSTATE)).setParts();
//			CharacterManager.Save();
		}
		if(getID().equalsIgnoreCase("ccback")) {
			GameStateManager.setState(GameStateManager.CHARACTERSTATE);
		}
		if(getID().equalsIgnoreCase("cchat")) {
			CharacterCustomization.state = CharacterCustomization.STATE_HAT;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesHat.size();i++) {
				Category c = Game.clothingRegistry.categoriesHat.get(i);
				GuiButton b = new GuiButton("categoryHat_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_HAT, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemHat_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenHat = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_HAT, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("ccovercoat")) {
			CharacterCustomization.state = CharacterCustomization.STATE_OVERCOAT;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesOvercoat.size();i++) {
				Category c = Game.clothingRegistry.categoriesOvercoat.get(i);
				GuiButton b = new GuiButton("categoryOvercoat_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_OVERCOAT, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemOvercoat_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenOvercoat = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_OVERCOAT, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("cctop")) {
			CharacterCustomization.state = CharacterCustomization.STATE_TOP;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesTop.size();i++) {
				Category c = Game.clothingRegistry.categoriesTop.get(i);
				GuiButton b = new GuiButton("categoryTop_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_TOP, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemTop_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenTop = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_TOP, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("cclegs")) {
			CharacterCustomization.state = CharacterCustomization.STATE_LEGS;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesLegs.size();i++) {
				Category c = Game.clothingRegistry.categoriesLegs.get(i);
				GuiButton b = new GuiButton("categoryLegs_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_LEGS, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemLegs_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenLegs = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_LEGS, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("ccfeet")) {
			CharacterCustomization.state = CharacterCustomization.STATE_FEET;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesFeet.size();i++) {
				Category c = Game.clothingRegistry.categoriesFeet.get(i);
				GuiButton b = new GuiButton("categoryFeet_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_FEET, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemFeet_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenFeet = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_FEET, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("ccglasses")) {
			CharacterCustomization.state = CharacterCustomization.STATE_GLASSES;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesGlasses.size();i++) {
				Category c = Game.clothingRegistry.categoriesGlasses.get(i);
				GuiButton b = new GuiButton("categoryGlasses_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_GLASSES, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemGlasses_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenGlasses = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_GLASSES, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("ccmask")) {
			CharacterCustomization.state = CharacterCustomization.STATE_MASK;
			CharacterCustomization.buttonsCategories = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.categoriesMask.size();i++) {
				Category c = Game.clothingRegistry.categoriesMask.get(i);
				GuiButton b = new GuiButton("categoryMask_" + c.getName());
				String name = c.getName();
				b.setTitle(c.getName());
				b.setUse(new PAction() {
					@Override
					public void command() {
						CharacterCustomization.currentCategory = Game.clothingRegistry.getCategory(
								ClothingRegistry.TYPE_MASK, name);
						CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
						for (int i=0;i < c.content().size();i++) {
							ClothingItem item = c.content().get(i);
							if(!item.isLocked()) {
								GuiButton b = new GuiButton("itemMask_" + item.getName());
								b.setTitle(item.getName());
								b.setUse(new PAction() {
									@Override
									public void command() {
										CharacterCustomization.chosenMask = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_MASK, item.getName());
									}
								});
								CharacterCustomization.buttonsItems.add(b);
							}
						}
					}
				});
				CharacterCustomization.buttonsCategories.add(b);
			}
		}
		if(getID().equalsIgnoreCase("ccset")) {
			CharacterCustomization.state = CharacterCustomization.STATE_SET;
			CharacterCustomization.buttonsItems = new ArrayList<GuiButton>();
			for (int i=0;i < Game.clothingRegistry.sets.size();i++) {
				if(i != 0) {
					ClothingItem item = Game.clothingRegistry.sets.get(i);
					if(!item.isLocked()) {
						GuiButton b = new GuiButton("itemSet_" + item.getName());
						b.setTitle(item.getName());
						b.setUse(new PAction() {
							@Override
							public void command() {
								CharacterCustomization.chosenSet = Game.clothingRegistry.getItem(ClothingRegistry.TYPE_SET, item.getName());
							}
						});
						CharacterCustomization.buttonsItems.add(b);
					}
				}
			}
		}
		if(getID().equalsIgnoreCase("cchatnone")) {
			CharacterCustomization.chosenHat = 0;
		}
		if(getID().equalsIgnoreCase("ccovercoatnone")) {
			CharacterCustomization.chosenOvercoat = 0;
		}
		if(getID().equalsIgnoreCase("cctopnone")) {
			CharacterCustomization.chosenTop = 0;
		}
		if(getID().equalsIgnoreCase("cclegsnone")) {
			CharacterCustomization.chosenLegs = 0;
		}
		if(getID().equalsIgnoreCase("ccfeetnone")) {
			CharacterCustomization.chosenFeet = 0;
		}
		if(getID().equalsIgnoreCase("ccglassesnone")) {
			CharacterCustomization.chosenGlasses = 0;
		}
		if(getID().equalsIgnoreCase("ccmasknone")) {
			CharacterCustomization.chosenMask = 0;
		}
		if(getID().equalsIgnoreCase("ccsetnone")) {
			CharacterCustomization.chosenSet = 0;
		}
		if(getID().equalsIgnoreCase("ccnext")) {
			CharacterCustomization.page++;
		}
		if(getID().equalsIgnoreCase("ccprev")) {
			CharacterCustomization.page--;
		}
		((CharacterCustomization) GameStateManager.gameStates.get(GameStateManager.CHARACTERSTATE)).setParts();
	}
}
