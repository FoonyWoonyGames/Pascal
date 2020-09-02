package HUD;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Util.PColor;

public class HudDialoguePaper extends HudDialogue {
	
	public int size = 45;
	public boolean centered = false;

	public HudDialoguePaper(String str) {
		super(str);
    	try {
			box = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/paper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics2D g) {
		if(showing) {
			somethingShowing = true;
			g.drawImage(box, 960-box.getWidth()/2, 200, null);
			g.setFont(new Font("Arial", Font.BOLD, size));
			if(!centered) {
				for (int i=0;i < lines.size();i++) {
					guimaster.drawColoredString(lines.get(i), 690, 270+40*i, PColor.BLACK, g);
				}
			} else {
				for (int i=0;i < lines.size();i++) {
					guimaster.drawCenteredColoredString(lines.get(i), 960, 270+40*i, PColor.BLACK, g);
				}
			}
			g.setFont(new Font("Arial", Font.BOLD, 50));
			guimaster.drawCenteredColoredOutlinedString("PRESS E TO CLOSE", 960, 1060, PColor.WHITE, PColor.BLACK, 1, g);
		}
	}
	public void setSize(int i) {
		size = i;
	}
	public void setCentered(boolean b) {
		centered = b;
	}

}
