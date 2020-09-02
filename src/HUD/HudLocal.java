package HUD;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;

import GUI.GuiMaster;
import GameState.GameStateManager;
import GameState.InGameLocal;
import Pascal.Game;
import Util.PColor;
import Util.PInteger;

public class HudLocal {
	private GuiMaster guim;
	public HudLocal() {
		guim = new GuiMaster();
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
		g.setComposite(c);
		if(InGameLocal.playerAmount != 3) {
			g.fillRoundRect(1600, 900, 250, 115, 30, 30);
		} else {
			g.fillRoundRect(1600, 900, 250, 155, 30, 30);
		}
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(c);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		guim.drawCenteredColoredOutlinedString("Deaths", 1725, 935, PColor.WHITE, PColor.BLACK, 1, g);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		guim.drawColoredOutlinedString(InGameLocal.player01.name + ":", 1610, 970, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawColoredOutlinedString(InGameLocal.player02.name + ":", 1610, 1000, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawBackwardsColoredOutlinedString(InGameLocal.player01.deaths + "", 1840, 970, PColor.WHITE, PColor.BLACK, 1, g);
		guim.drawBackwardsColoredOutlinedString(InGameLocal.player02.deaths + "", 1840, 1000, PColor.WHITE, PColor.BLACK, 1, g);
		if(InGameLocal.playerAmount == 3) {
			guim.drawColoredOutlinedString(InGameLocal.player03.name + ":", 1610, 1030, PColor.WHITE, PColor.BLACK, 1, g);
			guim.drawBackwardsColoredOutlinedString(InGameLocal.player03.deaths + "", 1840, 1030, PColor.WHITE, PColor.BLACK, 1, g);
		}
		g.setComposite(c);
		if(GameStateManager.showPos()) {
			g.setFont(new Font("Arial", Font.BOLD, 25));
			if(!Game.settings.locationformat) {
				guim.drawColoredOutlinedString("X: " + InGameLocal.player01.getx(), 40, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Y: " + InGameLocal.player01.gety(), 40, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				
				guim.drawColoredOutlinedString("X: " + InGameLocal.player02.getx(), 240, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Y: " + InGameLocal.player02.gety(), 240, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				
				if(InGameLocal.playerAmount == 3) {
					guim.drawColoredOutlinedString("X: " + InGameLocal.player03.getx(), 440, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
					guim.drawColoredOutlinedString("Y: " + InGameLocal.player03.gety(), 440, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				}
			}
			else {
				double x = (double) InGameLocal.player01.getx()/120;
				double y = (double) (4800 - InGameLocal.player01.gety())/120;
				guim.drawColoredOutlinedString("X: " + PInteger.round(x, 2), 40, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Y: " + PInteger.round(y, 2), 40, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);

				x = (double) InGameLocal.player02.getx()/120;
				y = (double) (4800 - InGameLocal.player02.gety())/120;
				guim.drawColoredOutlinedString("X: " + PInteger.round(x, 2), 240, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				guim.drawColoredOutlinedString("Y: " + PInteger.round(y, 2), 240, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				
				if(InGameLocal.playerAmount == 3) {
					x = (double) InGameLocal.player03.getx()/120;
					y = (double) (4800 - InGameLocal.player03.gety())/120;
					guim.drawColoredOutlinedString("X: " + PInteger.round(x, 2), 440, 1030, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
					guim.drawColoredOutlinedString("Y: " + PInteger.round(y, 2), 440, 1060, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
				}
			}
		}
	}
}
