package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.naming.ConfigurationException;

import Pascal.Game;
import TileMap.Background;
import Util.PAction;
import Util.PColor;
import Util.PTime;

public class GuiMain extends GuiScreen {
	public String randomString;
	private GuiButton buttonSingleplayer = new GuiButton("singleplayer");
	private GuiButton buttonMultiplayer = new GuiButton("multiplayer");
	private GuiButton buttonSettings = new GuiButton("settings");
	private GuiButton buttonQuit = new GuiButton("quit");
	
	private Background bg;

	private BufferedImage logo;
	private BufferedImage plogo;
	
	public GuiMain() {
		try {
		    buttonSingleplayer.setTitle("Play");
			buttonMultiplayer.setTitle("Multiplayer");
		    buttonSettings.setTitle("Settings");
		    buttonQuit.setTitle("Exit Game");
			bg = new Background("/textures/background/menu.png", 1);
	        if(PTime.getMonth() == PTime.MONTH_DECEMBER) {
				bg = new Background("/textures/background/snow/menu.png", 1);
	        }
		

		    try {
		        logo = ImageIO.read(getClass().getResourceAsStream("/textures/gui/menu/fwg.png"));
		        plogo = ImageIO.read(getClass().getResourceAsStream("/textures/gui/menu/logo.png"));
		        if(PTime.getMonth() == PTime.MONTH_DECEMBER) {
			        plogo = ImageIO.read(getClass().getResourceAsStream("/textures/gui/snow/logo.png"));
		        }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/text/message.txt")));
			ArrayList<String> lines = new ArrayList<String>();

			String line = reader.readLine();

			while( line != null ) {
			    lines.add(line);
			    line = reader.readLine();
			}
			
			// Funny line
			GregorianCalendar c = new GregorianCalendar();
			String currentDate = c.get(Calendar.MONTH)+":"+c.get(Calendar.DATE);
			String dateChristmas = "11:24";
			String dateChristmas2 = "11:25";
			String dateNewYear = "11:31";
			String dateNewYear2 = "0:1";
			String dateHalloween = "9:31";
			String dateValentine = "1:14";
			String dateEaster = "2:27";
			String dateMatilde = "1:23";
			String dateFade = "2:25";
			String dateMichael = "8:6";
			String dateAndy = "9:15";
			String dateBuggie = "0:17";
			String datePascal = "11:9";
			
			
			Random r = new Random();
			randomString = lines.get(r.nextInt(lines.size()));
			reader.close();
			if(currentDate.startsWith("11:")) {
				if(randomString.equalsIgnoreCase("SOS, it's christmas!")) {
					randomString = "SOS, it's christmas!";
				}
			}
			if(currentDate.equals(dateChristmas2) || currentDate.equals(dateChristmas)){
				randomString = "Merry Christmas! =D";
			}
			if(currentDate.equals(dateNewYear) || currentDate.equals(dateNewYear2)){
				randomString = "Happy New year! =D";
			}
			if(currentDate.equals(dateHalloween)){
				randomString = "Happy Halloween! >:)";
			}
			if(currentDate.equals(dateValentine)){
				randomString = "Happy Valentinesday! <3";
			}
			if(currentDate.equals(dateEaster)){
				randomString = "Happy Easter! ";
			}
			if(currentDate.equals(dateMatilde)){
				randomString = "Happy birthday, Matilde! =)";
			}
			if(currentDate.equals(dateFade)){
				randomString = "Happy birthday, Fade! =)";
			}
			if(currentDate.equals(dateMichael)){
				randomString = "Happy birthday, Michael! =)";
			}
			if(currentDate.equals(dateAndy)){
				randomString = "Happy birthday, Andy! =)";
			}
			if(currentDate.equals(dateBuggie)){
				randomString = "Happy birthday, Buggie! =)";
			}
			if(currentDate.equals(datePascal)){
				randomString = "Happy birthday, Pascal!!! =D =D =D";
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		drawLogo(g);
		drawPLogo(g);
		drawCopyright(g);
		
		
	    buttonSingleplayer.draw(960, 480, g);
	    buttonMultiplayer.draw(960, 570, g);
	    buttonSettings.draw(960, 660, g);
	    buttonQuit.draw(960, 750, g);
	    
	    
	    g.setFont(new Font("Arial", Font.BOLD, 35));
	    guim.drawBackwardsColoredOutlinedString("• " + Game.username, 1900, 40, PColor.LIGHT_GREEN, PColor.BLACK, 1, g);
	    

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		FontMetrics fm = g.getFontMetrics();
		if(fm.stringWidth(randomString) > 418) {
			g.setFont(new Font("Arial", Font.PLAIN, 24-((fm.stringWidth(randomString)-430)/15)));
		}
		fm = g.getFontMetrics();
		g.setColor(PColor.BLACK);
		g.fillRoundRect(940-(fm.stringWidth(randomString)/2), 1020, 40+(fm.stringWidth(randomString)), 100, 20, 20);

		g.setColor(PColor.DARK_GRAY);
		g.fillRoundRect(950-(fm.stringWidth(randomString)/2), 1030, 20+(fm.stringWidth(randomString)), 60, 20, 20);
		
		g.setColor(PColor.WHITE);
		guim.drawCenteredString(randomString, 960, 1060, g);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);

	}

	public void drawLogo(Graphics2D g) {
	    g.drawImage(logo.getScaledInstance(logo.getWidth()/2, logo.getHeight()/2, BufferedImage.SCALE_FAST), 1650, 900, null);
	}
	public void drawPLogo(Graphics2D g) {
 		g.drawImage(plogo, 960-(plogo.getWidth()/2), 60, null);
	}
	public void drawCopyright(Graphics2D g) {
		g.setFont(new Font("Arial", Font.ITALIC, 24));
		g.setColor(Color.WHITE);
		g.drawString("COPYRIGHT 2016 - FOONY WOONY GAMES", 20, 1040);
		g.drawString("PLEASE DO NOT DISTRIBUTE", 20, 1065);
	}
}
