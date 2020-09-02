package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import HUD.HudMaster;
import Pascal.Game;
import TileMap.Background;

public class GuiSettings extends GuiScreen {

	private Background bg;
	
	private GuiButton buttonBack = new GuiButton("masterback");
	private GuiButton buttonAudio = new GuiButton("audio");
	private GuiButton buttonControls = new GuiButton("controls");
	private GuiButton buttonAdvanced = new GuiButton("advanced");
	private GuiButton buttonGraphics = new GuiButton("graphics");
	private GuiButton buttonSocial = new GuiButton("social");
	private GuiButton buttonRestore = new GuiButton("restore");
	
	public GuiSettings() {
		try {
			buttonGraphics.setTitle("Graphics...");
			buttonControls.setTitle("Controls...");
			buttonAudio.setTitle("Audio...");
			buttonSocial.setTitle("Account/Social...");
			buttonAdvanced.setTitle("Advanced...");
			buttonRestore.setTitle("Restore All Settings");
			buttonBack.setTitle("Main Menu");
			
			buttonControls.setDisabled(true);
			
			
			bg = new Background("/textures/background/black.png", 1);

			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);

		HudMaster.drawVersion(g);
		
	    g.setFont(new Font("Arial", Font.BOLD, 50));
	    g.setColor(Color.WHITE);
		guim.drawCenteredString(Game.lang.guiSettings, 960, 120, g);
		buttonGraphics.draw(960, 200, g);
		buttonAudio.draw(960, 300, g);
		buttonControls.draw(960, 400, g);
		buttonSocial.draw(960, 500, g);
		buttonAdvanced.draw(960, 600, g);
		buttonRestore.draw(960, 750, g);
		buttonBack.draw(960, 980, g);

	}
}
