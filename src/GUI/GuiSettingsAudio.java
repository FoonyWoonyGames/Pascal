package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.naming.ConfigurationException;

import Pascal.Game;
import TileMap.Background;
import Util.PAction;
import Util.PColor;

public class GuiSettingsAudio extends GuiScreen {
	private Background bg;
	
	public static String randomString;
	private GuiSlider sliderMusic = new GuiSlider();
	private GuiSlider sliderAmbient = new GuiSlider();
	private GuiSlider sliderFootsteps = new GuiSlider();
	private GuiSlider sliderItems = new GuiSlider();
	private GuiSlider sliderInterface = new GuiSlider();
	private GuiSlider sliderMisc = new GuiSlider();
	private GuiButton buttonBack = new GuiButton("backtosettings");
	
	public GuiSettingsAudio() {

		try {
			buttonBack.setTitle(Game.lang.guiBack);
			buttonBack.setType(GuiButton.TYPE_BOXHALF);
			bg = new Background("/textures/background/black.png", 1);
			
			sliderMusic.showValue(true);
			sliderAmbient.showValue(true);
			sliderFootsteps.showValue(true);
			sliderItems.showValue(true);
			sliderInterface.showValue(true);
			sliderMisc.showValue(true);
			
			sliderMusic.setDisabled(true);
			sliderFootsteps.setDisabled(true);
			

			sliderMusic.setValue(Game.settings.soundMusic);
	        sliderMusic.setValueListener(new PAction() {
				public void command() {
					try {
						Game.settings.setValue("soundMusic", sliderMusic.getValue() + "");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
	        });
			sliderAmbient.setValue(Game.settings.soundAmbient);
	        sliderAmbient.setValueListener(new PAction() {
				public void command() {
					try {
						Game.settings.setValue("soundAmbient", sliderAmbient.getValue() + "");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
	        });
			sliderFootsteps.setValue(Game.settings.soundFootsteps);
	        sliderFootsteps.setValueListener(new PAction() {
				public void command() {
					try {
						Game.settings.setValue("soundFootsteps", sliderFootsteps.getValue() + "");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
	        });
			sliderItems.setValue(Game.settings.soundItems);
	        sliderItems.setValueListener(new PAction() {
				public void command() {
					try {
						Game.settings.setValue("soundItems", sliderItems.getValue() + "");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
	        });
			sliderInterface.setValue(Game.settings.soundInterface);
	        sliderInterface.setValueListener(new PAction() {
				public void command() {
					try {
						Game.settings.setValue("soundInterface", sliderInterface.getValue() + "");
					} catch (ConfigurationException | IOException e) {
						e.printStackTrace();
					}
				}
	        });
			sliderMisc.setValue(Game.settings.soundMisc);
	        sliderMisc.setValueListener(new PAction() {
				public void command() {
					try {
						Game.settings.setValue("soundMisc", sliderMisc.getValue() + "");
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
		guim.drawCenteredString("Audio", 960, 120, g);
		

		buttonBack.draw(150, 980, g);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		guim.drawCenteredColoredOutlinedString("Music Volume", 960, 180, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Ambient Volume", 960, 320, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Footsteps Volume", 960, 460, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Items Volume", 960, 600, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Interface Volume", 960, 740, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawCenteredColoredOutlinedString("Misc Volume", 960, 880, PColor.WHITE, PColor.BLACK, 1, g);
		sliderMusic.draw(960, 200, g);
		sliderAmbient.draw(960, 340, g);
		sliderFootsteps.draw(960, 480, g);
		sliderItems.draw(960, 620, g);
		sliderInterface.draw(960, 760, g);
		sliderMisc.draw(960, 900, g);


	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.PLAIN, 20));
	}
	
	public void update() {
	}
}
