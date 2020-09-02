package HUD;

import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Util.PColor;

public class HudDialogueOption extends HudDialogue {
	private ArrayList<String> options;
	public static ArrayList<HudDialogueOption> DialogueOptions = new ArrayList<HudDialogueOption>();
	private BufferedImage box;
	private boolean showing;

	private ActionListener action;
	public HudDialogueOption(String str) {
		super(str);
		options = new ArrayList<String>();
		DialogueOptions.add(this);
    	try {
			box = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/options.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g) {
		if(showing) {
		    g.setColor(PColor.BLACK);
		    g.fillRoundRect(1395, 235, 410, 510, 30, 30);
		    g.setColor(PColor.WHITE);
		    g.fillRoundRect(1400, 240, 400, 500, 30, 30);
		    g.setColor(PColor.BLACK);
		    g.setFont(new Font("Arial", Font.BOLD, 40));
		    g.drawString("SELECT AN OPTION", 1405, 280);
		    g.setFont(new Font("Arial", Font.BOLD, 15));
		    g.drawString("Press a number on your keyboard", 1405, 300);
		    g.setFont(new Font("Arial", Font.BOLD, 20));
			for (int i=0;i < options.size();i++) {
				int optionno = i + 1;
				g.drawString("(" + optionno + ") " + options.get(i), 1420, 340 + i*g.getFontMetrics().getHeight());
			}
		}
	}
	public void setShowing(boolean b) { showing = b; }
	public boolean isShowing() { return showing; }
	public void addOption(String str) { options.add(str); }
	public void setUse(ActionListener a) {
		action = a;
	}
	public void use(int i) {
		if(action != null) {
			setShowing(false);
			ActionEvent event = new ActionEvent(Event.ACTION_EVENT, i, "Used " + i);
			action.actionPerformed(event);
		}
		else {
			System.err.println("Use does not exist!");
		}
	}
}
