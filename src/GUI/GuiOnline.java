package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import TileMap.Background;

public class GuiOnline extends GuiScreen {
	private Background bg;
	
	private Font font;
	public static String randomString;
//	private GuiButton guib = new GuiButton();
	private GuiButton buttonJoin = new GuiButton("mpjoin");
	private GuiButton buttonBack = new GuiButton("onlineback");
	public GuiTextfield guit = new GuiTextfield("ipaddress");
	public static GuiTextfield guit2 = new GuiTextfield("portaddress");
	public static String joining;
	public static String iptojoin;
	public static String porttojoin;
	public GuiOnline() {
		try {
			bg = new Background("/textures/background/black.png", 1);
			font = new Font("Arial", Font.BOLD, 25);
			
			buttonJoin.setTitle("Join server");
			buttonBack.setTitle("Back");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		guit.setFocused(true);
		guit2.setFocused(false);
		joining = "false";
		guit.message = "";
		guit2.message = "";
	}
	public void draw(Graphics2D g) {
		guit.update();
		guit2.update();
		
		bg.draw(g);

	    if (joining.equalsIgnoreCase("false")) {
			BufferedImage online = null;
		    try {
		    	online = ImageIO.read(getClass().getResourceAsStream("/menu/online.png"));
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		    g.drawImage(online, -100, -350, null);
			
			
		    g.setFont(font);
		    g.setColor(Color.WHITE);
			g.drawString("Enter IP adress", 350, 290);
			g.drawString("Enter Port", 380, 375);
		    if (guit.message.length() < 1) {
			    buttonJoin.setDisabled(true);
		    }
		    else {
			    buttonJoin.setDisabled(false);
		    }
	    	buttonJoin.draw(320, 440, g);
			buttonBack.draw(5, 450, g);
		    
		    guit.draw(270, 300, g);
		    guit2.setWidth(150);
		    guit2.draw(370, 380, g);
	    	
	    }
	    
	    if (joining.equalsIgnoreCase("true")) {
	    	iptojoin = guit.message;
	    	porttojoin = guit2.message;
		    g.setFont(font);
		    g.setColor(Color.WHITE);
		    if (iptojoin.contains(":")) {
				g.drawString("Joining " + iptojoin + " ...", 350, 290);
		    }
		    else if (!porttojoin.isEmpty()) {
				g.drawString("Joining " + iptojoin + ":" + porttojoin + " ...", 350, 290);
		    }
		    else {
				g.drawString("Joining " + iptojoin + ":30125 ...", 350, 290);
		    }
	    }
	}
}
