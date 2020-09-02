package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.naming.ConfigurationException;
import Pascal.Game;
import TileMap.Background;
import Util.PAction;

public class GuiSettingsAdvanced extends GuiScreen {
	private Background bg;
	
	public static String randomString;
	private GuiButton buttonFocus = new GuiButton("focus");
	private GuiButton buttonCursor = new GuiButton("cursormode");
	private GuiButton buttonAutosave = new GuiButton("autosave");
	private GuiButton buttonAutosaveTime = new GuiButton("autosavetime");
	private GuiButton buttonWindow = new GuiButton("windowmode");
	private GuiButton buttonLocation = new GuiButton("locationformat");
	private GuiButton buttonNicknames = new GuiButton("shownicknames");
	private GuiButton buttonBack = new GuiButton("backtosettings");

	private String[] cursormodes = {
			"Normal",
			"Classic"
	};
	private int cursormode = Game.settings.cursormode;

	private String[] windowmodes = {
			"Windowed",
			"Borderless",
			"Fullscreen"
	};
	private int windowmode = Game.settings.fullscreen;
	
	private int[] autosavetime_values = {
			30000,
			60000,
			120000,
			180000,
			300000,
			600000,
			900000,
			1200000,
			1500000,
			1800000,
			2400000,
			3000000,
			3600000
	};
	private int autosavetime = 0;
	
	public GuiSettingsAdvanced() {
		try {
			bg = new Background("/textures/background/black.png", 1);

			buttonBack.setTitle(Game.lang.guiBack);
			buttonBack.setType(GuiButton.TYPE_BOXHALF);
			
			buttonFocus.setUse(new PAction() {
				@Override
				public void command() {
					Game.settings.toggle("focusscreen");
				}
			});
			buttonLocation.setUse(new PAction() {
				@Override
				public void command() {
					Game.settings.toggle("locationformat");
				}
			});
			buttonAutosave.setUse(new PAction() {
				@Override
				public void command() {
					Game.settings.toggle("autosave");
					buttonAutosaveTime.setDisabled(!Game.settings.autosave);
				}
			});
			buttonAutosaveTime.setUse(new PAction() {
				@Override
				public void command() {
					autosavetime++;
					if(autosavetime > autosavetime_values.length-1) {
						autosavetime = 0;
					}
					try {
						Game.settings.setValue("autosavetime", autosavetime_values[autosavetime]+"");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
			});
			buttonCursor.setUse(new PAction() {
				@Override
				public void command() {
					cursormode++;
					if(cursormode > cursormodes.length-1) {
						cursormode = 0;
					}
					try {
						Game.settings.setValue("cursormode", cursormode+"");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
			});
			buttonWindow.setUse(new PAction() {
				@Override
				public void command() {
					windowmode++;
					if(windowmode > windowmodes.length-1) {
						windowmode = 0;
					}
					try {
						Game.settings.setValue("fullscreen", windowmode+"");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
			});
		
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g) {
		update();
		bg.draw(g);
		
	    g.setFont(new Font("Arial", Font.BOLD, 50));
	    g.setColor(Color.WHITE);
		guim.drawCenteredString("Advanced", 960, 120, g);

		buttonBack.draw(150, 980, g);
		buttonFocus.draw(960, 200, g);
		buttonCursor.draw(960, 300, g);
		buttonLocation.draw(960, 400, g);
		buttonWindow.draw(960, 500, g);
		buttonAutosave.draw(960, 600, g);
		buttonAutosaveTime.draw(960, 700, g);
		buttonNicknames.draw(960, 800, g);


	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.PLAIN, 20));
	}
	
	public void update() {
		if(!Game.settings.focus) {
			buttonFocus.setTitle("Focus pause: OFF");
		}
		if(Game.settings.focus) {
			buttonFocus.setTitle("Focus pause: ON");
		}
		if(!Game.settings.locationformat) {
			buttonLocation.setTitle("Location: Advanced");
		}
		if(Game.settings.locationformat) {
			buttonLocation.setTitle("Location: Simple");
		}
		if(!Game.settings.autosave) {
			buttonAutosave.setTitle("Autosaving: OFF");
		}
		if(Game.settings.autosave) {
			buttonAutosave.setTitle("Autosaving: ON");
		}
		
		if(Game.settings.autosavetime == 30000) {
			buttonAutosaveTime.setTitle("Autosave every: 30s");
		}
		else if(Game.settings.autosavetime < 60000) {
			buttonAutosaveTime.setTitle("Autosave every: < 1m");
		}
		else {
			buttonAutosaveTime.setTitle("Autosave every: " + Game.settings.autosavetime / 60000 + "m");
		}

		if(!Game.settings.shownicknames) {
			buttonNicknames.setTitle("Show Usernames: OFF");
		}
		if(Game.settings.shownicknames) {
			buttonNicknames.setTitle("Show Usernames: ON");
		}
		
		buttonCursor.setTitle("Cursor: " + cursormodes[cursormode]);
		
		buttonWindow.setTitle("Window: " + windowmodes[windowmode]);
		
	}
}
