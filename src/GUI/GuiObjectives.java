package GUI;

import java.awt.Font;
import java.awt.Graphics2D;
import GameState.InGame;
import Util.PAction;
import Util.PColor;

public class GuiObjectives extends GuiScreen {

	public static GuiButton buttonBack = new GuiButton("optionsback");
	public static GuiButton buttonNext = new GuiButton("objectivesNext");
	public static GuiButton buttonPrev = new GuiButton("objectivesPrev");
	public GuiObjectives() {
		buttonBack.setType(GuiButton.TYPE_BOXHALF);
		buttonNext.setType(GuiButton.TYPE_ARROWRIGHT);
		buttonPrev.setType(GuiButton.TYPE_ARROWLEFT);
	}
	public void draw(Graphics2D g) {
		
		if(!InGame.objm.noObjective()) {
			g.setFont(new Font("Arial", Font.BOLD, 40));
			guim.drawColoredOutlinedString("Current Objective:", 200, 200, PColor.WHITE, PColor.BLACK, 1, g);
			g.setFont(new Font("Arial", Font.BOLD, 60));
			guim.drawColoredOutlinedString(InGame.objm.getActiveObjective(InGame.objm.currentObj).getTitle(), 200, 300, PColor.OBJECTIVE, PColor.BLACK, 1, g);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			String[] longDesc = InGame.objm.getActiveObjective(InGame.objm.currentObj).getLongDesc().split("\n");
			for (int i=0;i < longDesc.length;i++) {
				guim.drawColoredOutlinedString(longDesc[i], 200, 360 + 30*i, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
			
			
			g.setFont(new Font("Arial", Font.BOLD, 20));
			guim.drawColoredOutlinedString("ID: " + InGame.objm.getActiveObjective(InGame.objm.currentObj).getID(), 1680, 1040, PColor.GRAY, PColor.BLACK, 1, g);
			guim.drawColoredOutlinedString("State: (" + (InGame.objm.getActiveObjective(InGame.objm.currentObj).getState()+1) + "/" + 
					(InGame.objm.getActiveObjective(InGame.objm.currentObj).getMaxStates()+1) + ")", 1680, 1020, PColor.GRAY, PColor.BLACK, 1, g);
			
			
			g.setFont(new Font("Arial", Font.BOLD, 30));
			guim.drawCenteredColoredOutlinedString("Current Objective", 290, 800, PColor.WHITE, PColor.BLACK, 1, g);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			guim.drawCenteredColoredOutlinedString("(" + (InGame.objm.currentObj+1) + "/" + (InGame.objm.getActiveObjectives()) + ")", 290, 856, PColor.WHITE, PColor.BLACK, 1, g);

			buttonNext.draw(360, 800, g);
			buttonPrev.draw(300, 800, g);
			
			if(InGame.objm.getActiveObjectives() == 1) {
				buttonNext.setDisabled(true);
				buttonPrev.setDisabled(true);
			}
			else {
				buttonNext.setDisabled(false);
				buttonPrev.setDisabled(false);
			}
		}
		else {
			g.setFont(new Font("Arial", Font.BOLD, 70));
			guim.drawColoredOutlinedString("No objectives active", 200, 200, PColor.ERROR, PColor.BLACK, 1, g);
		}

		buttonBack.setTitle("Back");
		buttonBack.draw(400, 940, g);

		buttonNext.setUse(new PAction() {
			@Override
			public void command() {
				InGame.objm.currentObj++;
				if(InGame.objm.currentObj >= InGame.objm.getActiveObjectives()) {
					InGame.objm.currentObj = 0;
				}
			}
		});
		buttonPrev.setUse(new PAction() {
			@Override
			public void command() {
				InGame.objm.currentObj--;
				if(InGame.objm.currentObj == -1) {
					InGame.objm.currentObj = InGame.objm.getActiveObjectives()-1;
				}
			}
		});
	}
	
}
