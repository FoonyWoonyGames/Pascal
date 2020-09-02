package HUD;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import GUI.GuiMaster;
import Pascal.Game;
import Pascal.GamePanel;
import Util.PColor;


public class HudMaster {
	public static Font vfont;
	private static GuiMaster guim = new GuiMaster();
	public static void drawVersion(Graphics2D g) {
			vfont = new Font("Arial", Font.BOLD, 36);
			g.setFont(vfont);
			guim.drawColoredOutlinedString(Game.version, 20, 40, PColor.WHITE, PColor.BLACK, 1, g);
			if (System.getProperty("user.dir").equalsIgnoreCase("C:\\Users\\Emil Parkel\\Documents\\PS\\PascalSimulator2015")) {
				g.setFont(new Font("Arial", Font.ITALIC, 26));
				guim.drawColoredOutlinedString("This version is under development", 20, 70, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Bugs and glitches are to be expected", 20, 100, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
		
	}
	
	public static void drawFps(Graphics2D g) {
		if (Game.showfps) {
			vfont = new Font("Arial", Font.BOLD, 36);
			g.setFont(vfont);
			g.setColor(Color.BLACK);
			Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g.setComposite(c);
			g.fillOval(1540, 20, 170, 70);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
			guim.drawColoredOutlinedString("FPS:", 1560, 70, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			guim.drawColoredOutlinedString(GamePanel.FPSCounter + "", 1650, 70, PColor.WHITE, PColor.BLACK, 1, g);
		}
	}
}
