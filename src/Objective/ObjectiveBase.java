package Objective;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import GUI.GuiMaster;
import GameState.InGame;
import Item.Item;
import Pascal.GamePanel;
import Util.PColor;

public abstract class ObjectiveBase {
	protected String id;
	protected String title;
	protected String descShort;
	protected String descLong;
	protected int rewardExp;
	protected int rewardMoney;
	protected ArrayList<Item> rewardItems;
	
	protected int state;
	protected int statesMax;
	
	protected boolean remove;
	private GuiMaster guimaster;
	
	public ObjectiveBase() {
		id = "0000";
		title = "Untitled";
		descShort = "No short description.";
		descLong = "No long description.";
		rewardExp = 0;
		rewardMoney = 0;
		rewardItems = new ArrayList<Item>();
		
		state = 0;
		statesMax = 0;
		
		guimaster = new GuiMaster();
	}
	public void init() {
		TimeOfText = System.currentTimeMillis();
		TimeOfStateText = System.currentTimeMillis();
		splash = "New objective";
		remove = false;
	}
	public void update() {
		if(state > statesMax) {
			state = statesMax;
		}
	}
	public int getState() {
		return state;
	}
	public int getMaxStates() {
		return statesMax;
	}
	public void setState(int s) {
		state = s;
		if(state > statesMax) {
			state = statesMax;
		}
	}
	public void nextState() {
		TimeOfStateText = System.currentTimeMillis();
	}
	public String getID() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getShortDesc() {
		return descShort;
	}
	public String getLongDesc() {
		return descLong;
	}
	String splash;
	public void complete() {
		for (int i=0;i < rewardItems.size();i++) {
			InGame.player.itemAdd(rewardItems.get(i));
		}
		InGame.cash += rewardMoney;
		TimeOfText = System.currentTimeMillis();
		splash = "Objective completed";
		remove = true;
		InGame.completedObjectives.add(id);
	}
	public void fail() {
		TimeOfText = System.currentTimeMillis();
		splash = "Objective failed";
		remove = true;
	}
	long TimeOfText;
	long TimeOfStateText;
	public void draw(Graphics2D g) {
		if(System.currentTimeMillis()-TimeOfText < 4000) {
			Composite c = null;
			long timePassed = System.currentTimeMillis()-TimeOfText;
			if(timePassed < 10) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
				g.setComposite(c);
			}
			if(timePassed > 10) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
				g.setComposite(c);
			}
			if(timePassed > 20) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
				g.setComposite(c);
			}
			if(timePassed > 30) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
				g.setComposite(c);
			}
			if(timePassed > 40) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
				g.setComposite(c);
			}
			if(timePassed > 50) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g.setComposite(c);
			}
			if(timePassed > 60) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g.setComposite(c);
			}
			if(timePassed > 70) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
				g.setComposite(c);
			}
			if(timePassed > 80) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
				g.setComposite(c);
			}
			if(timePassed > 90) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
				g.setComposite(c);
			}
			if(timePassed > 100) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
				g.setComposite(c);
			}
			if(timePassed > 3900) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
				g.setComposite(c);
			}
			if(timePassed > 3910) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
				g.setComposite(c);
			}
			if(timePassed > 3920) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
				g.setComposite(c);
			}
			if(timePassed > 3930) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
				g.setComposite(c);
			}
			if(timePassed > 3940) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g.setComposite(c);
			}
			if(timePassed > 3950) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g.setComposite(c);
			}
			if(timePassed > 3960) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
				g.setComposite(c);
			}
			if(timePassed > 3970) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
				g.setComposite(c);
			}
			if(timePassed > 3980) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
				g.setComposite(c);
			}
			if(timePassed > 3990) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
				g.setComposite(c);
			}
			if(timePassed > 4000) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
				g.setComposite(c);
			}
			g.setFont(new Font("Arial", Font.BOLD, 70));
			guimaster.drawCenteredColoredOutlinedString(
					title, 900, 400, PColor.OBJECTIVE, PColor.BLACK, 1, g);
			g.getFontMetrics().stringWidth(title);
			
			int width = g.getFontMetrics().stringWidth(title);
			g.setFont(new Font("Arial", Font.ITALIC, 40));
			guimaster.drawColoredOutlinedString(
					splash, 900 - width/2, 340, PColor.WHITE, PColor.BLACK, 1, g);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
		}
		else {
			if(remove) {
				InGame.objm.removeObjective(getID());
			}
		}

		if(System.currentTimeMillis()-TimeOfStateText < 4000) {
			Composite c = null;
			long timePassed = System.currentTimeMillis()-TimeOfStateText;
			if(timePassed < 10) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
				g.setComposite(c);
			}
			if(timePassed > 10) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
				g.setComposite(c);
			}
			if(timePassed > 20) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
				g.setComposite(c);
			}
			if(timePassed > 30) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
				g.setComposite(c);
			}
			if(timePassed > 40) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
				g.setComposite(c);
			}
			if(timePassed > 50) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g.setComposite(c);
			}
			if(timePassed > 60) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g.setComposite(c);
			}
			if(timePassed > 70) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
				g.setComposite(c);
			}
			if(timePassed > 80) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
				g.setComposite(c);
			}
			if(timePassed > 90) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
				g.setComposite(c);
			}
			if(timePassed > 100) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
				g.setComposite(c);
			}
			if(timePassed > 3900) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
				g.setComposite(c);
			}
			if(timePassed > 3910) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
				g.setComposite(c);
			}
			if(timePassed > 3920) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
				g.setComposite(c);
			}
			if(timePassed > 3930) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
				g.setComposite(c);
			}
			if(timePassed > 3940) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g.setComposite(c);
			}
			if(timePassed > 3950) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g.setComposite(c);
			}
			if(timePassed > 3960) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
				g.setComposite(c);
			}
			if(timePassed > 3970) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
				g.setComposite(c);
			}
			if(timePassed > 3980) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
				g.setComposite(c);
			}
			if(timePassed > 3990) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
				g.setComposite(c);
			}
			if(timePassed > 4000) {
				c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
				g.setComposite(c);
			}
			g.setFont(new Font("Arial", Font.BOLD, 40));
			guimaster.drawCenteredColoredOutlinedString(
					descShort, 960, 460, PColor.OBJECTIVE, PColor.BLACK, 1, g);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
		}
	}
}
