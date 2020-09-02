package GUI;

import java.awt.Font;
import java.awt.Graphics2D;

import GameState.GameStateManager;
import Util.PColor;

public class GuiExitConfirm extends GuiScreen {
	public static GuiButton buttonYes = new GuiButton("exitYes");
	public static GuiButton buttonNo = new GuiButton("exitNo");
	public static GuiButton buttonCancel = new GuiButton("exitCancel");
	public GuiExitConfirm() {
		buttonYes.gsm(GameStateManager.currentState);
		buttonNo.gsm(GameStateManager.currentState);
		buttonCancel.gsm(GameStateManager.currentState);
		
		buttonYes.setTitle("Yes");
		buttonNo.setTitle("No");
		buttonCancel.setTitle("Cancel");
		buttonNo.setType(GuiButton.TYPE_BOXHALF);
		buttonYes.setType(GuiButton.TYPE_BOXHALF);
	}
	public void draw(Graphics2D g) {

		g.setFont(new Font("Arial", Font.BOLD, 34));
		guim.drawCenteredColoredOutlinedString("You are about to exit, do you want to save your game?", 960, 400, PColor.WHITE, PColor.BLACK, 1, g);
		
		buttonYes.draw(827, 550, g);
		buttonNo.draw(1093, 550, g);
		buttonCancel.draw(960, 640, g);
		
	}
}
