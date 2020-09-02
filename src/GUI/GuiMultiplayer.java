package GUI;

import java.awt.Graphics2D;

import Pascal.Game;
import TileMap.Background;

public class GuiMultiplayer extends GuiScreen {
	private Background bg;
	private GuiButton buttonLocal = new GuiButton("local");
	private GuiButton buttonOnline = new GuiButton("online");
	private GuiButton buttonBack = new GuiButton("masterback");
	
	public GuiMultiplayer() {
		try {
			buttonLocal.setTitle("Local");
			buttonOnline.setTitle("Online");
			buttonOnline.setDisabled(true);
			buttonBack.setTitle("< Back");
			buttonBack.setType(GuiButton.TYPE_BOXHALF);

			bg = new Background("/textures/background/menu.png", 1);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		buttonLocal.draw(960, 480, g);
		buttonOnline.draw(960, 570, g);
		buttonBack.draw(150, 980, g);
	}
}
