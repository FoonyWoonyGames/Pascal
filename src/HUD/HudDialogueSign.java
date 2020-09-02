package HUD;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Util.PColor;

public class HudDialogueSign extends HudDialogue {

	public HudDialogueSign(String str) {
		super(str);
    	try {
			box = ImageIO.read(getClass().getResourceAsStream("/textures/object/" + str + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics2D g) {
		if(showing) {
			somethingShowing = true;
			g.drawImage(box.getScaledInstance(box.getWidth()*6, box.getHeight()*6, BufferedImage.SCALE_FAST), 960-(box.getWidth()*6)/2, 200, null);
			g.setFont(new Font("Arial", Font.BOLD, 90));
			if(lines.size() > 0) {
				guimaster.drawCenteredColoredOutlinedString(lines.get(0), 960, 470, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
			if(lines.size() > 1) {
				guimaster.drawCenteredColoredOutlinedString(lines.get(1), 960, 600, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
			if(lines.size() > 2) {
				guimaster.drawCenteredColoredOutlinedString(lines.get(2), 960, 730, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
			}
			g.setFont(new Font("Arial", Font.BOLD, 50));
			guimaster.drawCenteredColoredOutlinedString("PRESS E TO CLOSE", 960, 960, PColor.WHITE, PColor.BLACK, 1, g);
		}
	}

}
