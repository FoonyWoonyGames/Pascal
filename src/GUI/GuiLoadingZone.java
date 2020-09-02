package GUI;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.InGame;
import Pascal.Game;
import Pascal.GamePanel;
import Util.PColor;

public class GuiLoadingZone extends GuiScreen{
	
	private static GuiMaster guimaster = new GuiMaster();
	private static BufferedImage icon;
	
	private static String[] states = {
			"Player",					// 0
			"World",					// 1
			"World Properties",			// 2
			"Nothing! We're done!"		// 3
		};
	private static int state = 0;
	private static String name = "zone0";
	
	public GuiLoadingZone() {
        try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/gui/loading/zone.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(PColor.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setFont(new Font("Arial", Font.BOLD, 65));
		guimaster.drawCenteredColoredOutlinedString("LOADING ZONE", 960, 100, PColor.WHITE, PColor.DARK_GRAY, 1, g);
		g.setFont(new Font("Arial", Font.ITALIC, 25));
		guimaster.drawCenteredColoredOutlinedString(name, 960, 150, PColor.INTERFACE, PColor.DARK_GRAY, 1, g);
		

		g.setFont(new Font("Arial", Font.BOLD, 40));
		guimaster.drawCenteredColoredOutlinedString("Currently Loading:", 960, 550, PColor.WHITE, PColor.DARK_GRAY, 1, g);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		guimaster.drawCenteredColoredOutlinedString(states[state], 960, 600, PColor.INTERFACE, PColor.DARK_GRAY, 1, g);
		
		g.setColor(PColor.DARK_GRAY);
		g.fillRect(758, 798, 404, 24);
		g.setColor(PColor.GRAY);
		g.fillRect(760, 800, 400, 20);
		if(state != 0) {
			g.setColor(PColor.INTERFACE);
			g.fillRect(760, 800, (400*state)/(states.length-1), 20);
		}
		
		g.drawImage(icon, 960-(icon.getWidth()/2), 300, null);
	}
	public void setState(int s) {
		state = s;
	}
	public void setName(String s) {
		name = s;
	}
}