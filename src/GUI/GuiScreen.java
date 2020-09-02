package GUI;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import Entity.Player;
import GameState.GameStateManager;
import Pascal.Game;
import Pascal.GamePanel;
import TileMap.Background;
import Util.PColor;
import Util.PInteger;

public class GuiScreen {
	protected static GuiMaster guim = new GuiMaster();

	public void drawBackground(Graphics2D g) {
		g.setColor(PColor.BLACK);
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
		g.setComposite(c);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(c);
	}
	public void drawLocation(Player player, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 24));
		if(GameStateManager.showPos()) {
			if(!Game.settings.locationformat) {
				guim.drawColoredOutlinedString("X: " + player.getx(), 40, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Y: " + player.gety(), 40, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
			else {
				double x = (double) player.getx()/120;
				double y = (double) (4800 - player.gety())/120;
				guim.drawColoredOutlinedString("X: " + PInteger.round(x, 2), 40, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Y: " + PInteger.round(y, 2), 40, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
		}
	}

}
