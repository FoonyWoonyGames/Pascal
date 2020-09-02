package GUI;

import java.awt.Graphics2D;
import java.io.File;

public class GuiOptions extends GuiScreen {

	public static GuiButton buttonSave = new GuiButton("optionssave");
	public static GuiButton buttonLoad = new GuiButton("optionsload");
	public static GuiButton buttonSettings = new GuiButton("optionssettings");
	public static GuiButton buttonObjectives = new GuiButton("optionsobjectives");
	public static GuiButton buttonSocial = new GuiButton("optionssocial");
	public static GuiButton buttonAchievements = new GuiButton("optionsachievements");
	public static GuiButton buttonBack = new GuiButton("optionsback");
	
	public static void draw(Graphics2D g) {
		
		
		buttonSave.setTitle("Save");
		buttonLoad.setTitle("Load");
		buttonSettings.setTitle("Settings");
		buttonObjectives.setTitle("Objectives");
		buttonSocial.setTitle("Social");
		buttonAchievements.setTitle("Achievements");
		buttonBack.setTitle("Back");
		
		buttonSave.draw(640, 200, g);
		buttonLoad.draw(640, 300, g);
		buttonSettings.draw(640, 400, g);
		buttonObjectives.draw(1280, 200, g);
		buttonSocial.draw(1280, 300, g);
		buttonAchievements.draw(1280, 400, g);
		
		buttonSocial.setDisabled(true);
		buttonAchievements.setDisabled(true);
		if(new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/saves/save1.fwgs").exists()) {
			buttonLoad.setDisabled(false);
		}
		else {
			buttonLoad.setDisabled(true);
		}
		
		buttonBack.draw(960, 940, g);
	}
	
}
