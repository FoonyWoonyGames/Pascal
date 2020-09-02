package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.naming.ConfigurationException;
import Pascal.Game;
import TileMap.Background;
import Util.PAction;

public class GuiSettingsSocial extends GuiScreen {
	private Background bg;
	private GuiButton buttonBack = new GuiButton("backtosettings");
	private GuiButton buttonCharacterCustomization = new GuiButton("customchar");
	
	public GuiSettingsSocial() {
		try {

			buttonBack.setTitle("< Back");
			buttonBack.setType(GuiButton.TYPE_BOXHALF);
			buttonCharacterCustomization.setTitle("Character Customization...");

			bg = new Background("/textures/background/black.png", 1);
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
		guim.drawCenteredString("Logged in as: " + Game.username, 960, 120, g);

		buttonBack.draw(150, 980, g);
		buttonCharacterCustomization.draw(960, 200, g);


	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.PLAIN, 20));
	}
	
	public void update() {
	}
}
