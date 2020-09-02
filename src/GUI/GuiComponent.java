package GUI;

import java.awt.Graphics2D;
import Util.PAction;

public class GuiComponent extends GuiMaster {
	protected String id;
	protected PAction action;
	private boolean visible = true;
	public boolean addedtoFrame;
	
	protected int gsm;
	public GuiComponent(String str) {
		id = str;
	}
	public void setUse(PAction a) {
		action = a;
	}
	public void use() {
		if(action != null) {
			action.execute();
		}
	}
	public void draw(int x, int y, Graphics2D g) {
		// Empty function
	}
	public void update() {
		// Empty function
	}
	public void gsm(int gamest) {
		gsm = gamest;
	}
	public void setVisible(boolean onoff) {
		visible = onoff;
	}
	public boolean isVisible() {
		return visible;
	}
}
