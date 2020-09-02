package HUD;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import GUI.GuiMaster;
import Util.PColor;

public class HudSplash {
	private String text;
	private Color col;
	private boolean splashDrawn;
	private int y;
	private long splashTime;
	GuiMaster guim;
	public static ArrayList<HudSplash> Splashes = new ArrayList<HudSplash>();
	public HudSplash(String str, Color col) {
		text = str;
		this.col = col;
		Splashes.add(this);
		guim = new GuiMaster();
	}

	public void draw(Graphics2D g) {
		if(!splashDrawn) {
			splashDrawn = true;
			splashTime = System.currentTimeMillis();
			y = 300;
		}
		long timePassed = System.currentTimeMillis() - splashTime;
		Composite c = null;
		boolean changeY = false;
		if(timePassed < 10) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
			g.setComposite(c);
		}
		if(timePassed > 10) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			g.setComposite(c);
		}
		if(timePassed > 50) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g.setComposite(c);
		}
		if(timePassed > 100) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g.setComposite(c);
		}
		if(timePassed > 150) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g.setComposite(c);
		}
		if(timePassed > 200) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g.setComposite(c);
		}
		if(timePassed > 250) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g.setComposite(c);
		}
		if(timePassed > 300) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			g.setComposite(c);
		}
		if(timePassed > 350) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			g.setComposite(c);
		}
		if(timePassed > 400) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			g.setComposite(c);
		}
		if(timePassed > 500) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
		}
		
		
		
		if(timePassed > 1500) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			g.setComposite(c);
			changeY=true;
		}
		if(timePassed > 1550) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			g.setComposite(c);
		}
		if(timePassed > 1600) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			g.setComposite(c);
		}
		if(timePassed > 1650) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g.setComposite(c);
		}
		if(timePassed > 1700) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g.setComposite(c);
		}
		if(timePassed > 1750) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g.setComposite(c);
			changeY=true;
		}
		if(timePassed > 1800) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g.setComposite(c);
		}
		if(timePassed > 1850) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g.setComposite(c);
		}
		if(timePassed > 1900) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			g.setComposite(c);
		}
		if(timePassed > 1950) {
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
			g.setComposite(c);
		}
		if(changeY) {
			y--;
		}
		g.setFont(new Font("Arial", Font.BOLD, 50));
		guim.drawCenteredColoredOutlinedString(text, 960, y, col, PColor.BLACK, 1, g);
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(c);
		if(timePassed > 2000) {
			splashDrawn = false;
			Splashes.remove(this);
		}
	}
}
