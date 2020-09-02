package GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

import HUD.HudMaster;
import Settings.Controls;
import TileMap.Background;

public class PlayOnline extends GameState {

	private Background bg;
	
	private Font font;
	public static String randomString;
//	private GuiButton guib = new GuiButton();
	public static String joining;
	public static String iptojoin;
	public static String title;
	
	public PlayOnline(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			bg = new Background("/textures/background/black.png", 1);
			
		
			
			font = new Font("Arial", Font.ITALIC, 15);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		joining = "false";
//	    iptojoin = OnlineState.iptojoin;
	    title = "Loading motd ...";


	}
	public void update() {
	}
	public static void update2() {
		System.out.println("Hi!");
	}
	
	
	
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		HudMaster.drawVersion(g);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
//		drawCenteredString(title, 900, 350, g);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);

	}
	
	  public void drawCenteredString(String s, int w, int h, Graphics g) {
		    FontMetrics fm = g.getFontMetrics();
		    int x = (w - fm.stringWidth(s)) / 2;
		    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		    g.drawString(s, x, y);
		  }


	public void keyPressed(int k, char c) {
		Controls.pressGlobal(k);
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		Controls.mousereleaseGlobal(m);
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	
}