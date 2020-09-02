package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.naming.ConfigurationException;

import Pascal.Game;
import TileMap.Background;
import Util.PAction;

public class GuiSettingsGraphics extends GuiScreen {
	private Background bg;
	
	private Font font;
	public static String randomString;
	private GuiButton buttonAntiAliasing = new GuiButton("antialias");
	private GuiButton buttonBackground = new GuiButton("bgquality");
	private GuiButton buttonShadows = new GuiButton("shadows");
	private GuiButton buttonFog = new GuiButton("fog");
	private GuiButton buttonBack = new GuiButton("backtosettings");
	
	private String[] bgquality = {
			"Bad",
			"Good"
	};
	private int bgqualityCurrent;
	
	public GuiSettingsGraphics() {

		try {
			buttonBack.setTitle(Game.lang.guiBack);
			buttonBack.setType(GuiButton.TYPE_BOXHALF);
			bg = new Background("/textures/background/black.png", 1);
			
			bgqualityCurrent = Game.settings.bgquality;
			buttonBackground.setUse(new PAction() {
				@Override
				public void command() {
					bgqualityCurrent++;
					if(bgqualityCurrent == bgquality.length) {
						bgqualityCurrent = 0;
					}
					Game.settings.bgquality = bgqualityCurrent;
					try {
						Game.settings.setValue("bgquality", String.valueOf(bgqualityCurrent));
					} catch (ConfigurationException | IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			buttonFog.setUse(new PAction() {
				@Override
				public void command() {
					Game.settings.toggle("fog");
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
		guim.drawCenteredString("Graphics", 960, 120, g);

		buttonBack.draw(150, 980, g);
		buttonAntiAliasing.draw(960, 200, g);
		buttonBackground.draw(960, 300, g);
		buttonFog.draw(960, 400, g);
		buttonShadows.draw(960, 500, g);

	}
	
	public void update() {
	    // Anti-Alias
		if(!Game.settings.antialias) {
			buttonAntiAliasing.setTitle("Anti-Aliasing: OFF");
		}
		if(Game.settings.antialias) {
			buttonAntiAliasing.setTitle("Anti-Aliasing: ON");
		}
		// Background quality
		buttonBackground.setTitle("Background Quality: " + bgquality[bgqualityCurrent]);
		
	    // Shadows
		if(Game.settings.shadows) {
			buttonShadows.setTitle("Shadows: ON");
		}
		if(!Game.settings.shadows) {
			buttonShadows.setTitle("Shadows: OFF");
		}
		
	    // Shadows
		if(Game.settings.fog) {
			buttonFog.setTitle("Fog: ON");
		}
		if(!Game.settings.fog) {
			buttonFog.setTitle("Fog: OFF");
		}
	}
}
