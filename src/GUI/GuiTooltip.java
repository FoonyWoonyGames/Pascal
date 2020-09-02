package GUI;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class GuiTooltip extends GuiMaster {
	public static ArrayList<GuiTooltip> TTips = new ArrayList<GuiTooltip>();
	
	public abstract void draw(Graphics2D g);
	public static void drawTooltips(Graphics2D g) {
		if(TTips.size() > 0) {
			TTips.get(TTips.size()-1).draw(g);
		}
	}
}
