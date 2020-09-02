package GUI;

import java.awt.Font;
import java.awt.Graphics2D;

import GameState.GameStateManager;
import GameState.InGame;
import Pascal.Game;
import Util.PColor;

public class GuiDead extends GuiScreen{

	private static Font pfont;
	private static GuiMaster guimaster = new GuiMaster();
	private static GuiScreen guiscreen = new GuiScreen();
	public static GuiButton buttonRespawn = new GuiButton("respawn");
	public static GuiButton buttonMenu = new GuiButton("death_exittomenu");
	
	private static boolean hasFaded;
	
	public GuiDead() {
	    buttonRespawn.setTitle("Respawn");
	    buttonMenu.setTitle("Main menu");
		buttonMenu.setType(GuiButton.TYPE_BOXHALF);
	}
	public void draw(Graphics2D g) {
		if(System.currentTimeMillis() - InGame.player.getDeathTime() > 500) {
			g.setColor(PColor.BLACK);
			g.fillRect(0, 0, 1920, 1080);
			Font tfont = new Font("Arial", Font.BOLD, 90);
			g.setFont(tfont);
			String pausetitle = Game.lang.guiDied;
			guimaster.drawCenteredColoredOutlinedString(pausetitle, 960, 300, PColor.WHITE, PColor.BLACK, 2, g);
			pfont = new Font("Arial", Font.BOLD, 25);
			g.setFont(pfont);

		    buttonRespawn.draw(960, 360, g);
			buttonMenu.draw(150, 980, g);
		}
	}
}