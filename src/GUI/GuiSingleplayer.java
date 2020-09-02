package GUI;

import java.awt.Graphics2D;
import java.io.File;


import Pascal.Game;
import TileMap.Background;
import Util.PTime;

public class GuiSingleplayer extends GuiScreen {

	private GuiButton buttonNew = new GuiButton("gamenew");
	private GuiButton buttonLoad = new GuiButton("gameload");
	private GuiButton buttonBack = new GuiButton("masterback");
	
	private Background bg;
	
	private File f = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves/save1.fwgs");
	
	public GuiSingleplayer() {
		try {
			buttonNew.setTitle(Game.lang.guiSingleplayerNew);
			buttonLoad.setTitle(Game.lang.guiSingleplayerContinue);
			buttonBack.setTitle(Game.lang.guiBack);
			buttonBack.setType(GuiButton.TYPE_BOXHALF);
			
			bg = new Background("/textures/background/menu.png", 1);
	        if(PTime.getMonth() == PTime.MONTH_DECEMBER) {
				bg = new Background("/textures/background/snow/menu.png", 1);
	        }
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		
		checkSavefile();
		buttonLoad.draw(960, 480, g);
		buttonNew.draw(960, 570, g);
		buttonBack.draw(150, 980, g);
		
	}

	public void checkSavefile() {
		if(f.exists() && !f.isDirectory()) { 
		    buttonLoad.setDisabled(false);
		}
		else {
			buttonLoad.setDisabled(true);
		}
	}
}
