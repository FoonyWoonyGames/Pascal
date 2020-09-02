package GUI;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.InGame;
import Pascal.Game;
import Pascal.GamePanel;
import Util.PColor;

public class GuiPause extends GuiScreen{

	public static final int STATE_MAIN = 0;
	public static final int STATE_OPTIONS = 1;
	public static final int STATE_CONFIRMEXIT = 2;
	public static final int STATE_OBJECTIVES = 3;
	public static final int STATE_LOCAL = 4;
	
	private static GuiObjectives guiobjectives = new GuiObjectives();
	private static GuiExitConfirm guiexitconfirm = new GuiExitConfirm();
	
	private static GuiMaster guimaster = new GuiMaster();
	private static BufferedImage background;
	public static GuiButton buttonResume = new GuiButton("pauseresume");
	public static GuiButton buttonPoptions = new GuiButton("pauseoptions");
	public static GuiButton buttonMenu = new GuiButton("exittomenu");
	public static GuiButton buttonMenuForced = new GuiButton("forceexit");
	public static int state = STATE_MAIN;
	
	public GuiPause() {
		buttonResume.setTitle("Resume");
		buttonPoptions.setTitle("Options...");
		buttonMenu.setTitle("Main Menu");
		buttonMenuForced.setTitle("Main Menu");
		
		buttonResume.setType(GuiButton.TYPE_BOXHALF);
		buttonPoptions.setType(GuiButton.TYPE_BOXHALF);
		buttonMenu.setType(GuiButton.TYPE_BOXHALF);
		buttonMenuForced.setType(GuiButton.TYPE_BOXHALF);

        try {
			background = ImageIO.read(getClass().getResourceAsStream("/textures/gui/menu/pause.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		if(InGame.getZone().getAmbient().isRunning()) {
			InGame.getZone().getAmbient().pause();
		}
		g.setColor(PColor.BLACK);
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.90f);
		g.setComposite(c);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(c);
		g.drawImage(background, 671, 50, null);
		if(state == STATE_MAIN) {
			g.setColor(PColor.BLACK);
			g.fillRoundRect(810, 360, 300, 440, 50, 50);
			g.setColor(PColor.DARK_GRAY);
			g.fillRoundRect(820, 370, 280, 420, 30, 30);
			g.setFont(new Font("Arial", Font.BOLD, 45));
			String pausetitle = Game.lang.guiPaused;
			guimaster.drawCenteredColoredOutlinedString(pausetitle, 960, 300, PColor.WHITE, PColor.BLACK, 2, g);

			
		    buttonResume.draw(960, 380, g);
		    buttonPoptions.draw(960, 470, g);
		    buttonMenu.draw(960, 700, g);
		}
		else if (state == STATE_LOCAL) {
			g.setColor(PColor.BLACK);
			g.fillRoundRect(810, 460, 300, 240, 50, 50);
			g.setColor(PColor.DARK_GRAY);
			g.fillRoundRect(820, 470, 280, 220, 30, 30);
			g.setFont(new Font("Arial", Font.BOLD, 45));
			String pausetitle = Game.lang.guiPaused;
			guimaster.drawCenteredColoredOutlinedString(pausetitle, 960, 400, PColor.WHITE, PColor.BLACK, 2, g);

			
		    buttonResume.draw(960, 480, g);
		    buttonMenuForced.draw(960, 600, g);
		}
		else if (state == STATE_OPTIONS) {
			GuiOptions.draw(g);
		}
		else if (state == STATE_CONFIRMEXIT){
			guiexitconfirm.draw(g);
		}
		else if (state == STATE_OBJECTIVES) {
			guiobjectives.draw(g);
		}
	}
	public static void setState(int i) {
		state = i;
	}
}