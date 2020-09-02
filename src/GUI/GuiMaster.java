package GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GuiMaster {
	int ShiftNorth(int p, int distance) {
		   return (p - distance);
		   }
		int ShiftSouth(int p, int distance) {
		   return (p + distance);
		   }
		int ShiftEast(int p, int distance) {
		   return (p + distance);
		   }
		int ShiftWest(int p, int distance) {
		   return (p - distance);
		   }
	public void drawColoredString(String text, int x, int y, Color color, Graphics2D g) {
		g.setColor(color);
		g.drawString(text, x, y);
	}
	public void drawCenteredColoredString(String text, int x, int y, Color color, Graphics2D g) {
		g.setColor(color);
	    FontMetrics fm = g.getFontMetrics();
	    int x2 = (x - (fm.stringWidth(text) / 2));
		g.drawString(text, x2, y);
	}
	public void drawCenteredString(String text, int x, int y, Graphics2D g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x2 = (x - (fm.stringWidth(text) / 2));
		g.drawString(text, x2, y);
	}
	public void drawOutlinedString(String text, int x, int y, int ol, Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawString(text, ShiftWest(x, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftWest(x, ol), ShiftSouth(y, ol));
		g.drawString(text, ShiftEast(x, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftEast(x, ol), ShiftSouth(y, ol));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	public void drawColoredOutlinedString(String text, int x, int y, Color color, Color color2, int ol, Graphics2D g) {
		g.setColor(color2);
		g.drawString(text, ShiftWest(x, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftWest(x, ol), ShiftSouth(y, ol));
		g.drawString(text, ShiftEast(x, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftEast(x, ol), ShiftSouth(y, ol));
		g.setColor(color);
		g.drawString(text, x, y);
	}
	public void drawCenteredColoredOutlinedString(String text, int x, int y, Color color, Color color2, int ol, Graphics2D g) {
		g.setColor(color2);
	    FontMetrics fm = g.getFontMetrics();
	    int x2 = (x - (fm.stringWidth(text) / 2));
		g.drawString(text, ShiftWest(x2, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftWest(x2, ol), ShiftSouth(y, ol));
		g.drawString(text, ShiftEast(x2, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftEast(x2, ol), ShiftSouth(y, ol));
		g.setColor(color);
		g.drawString(text, x2, y);
	}
	public void drawBackwardsColoredOutlinedString(String text, int x, int y, Color color, Color color2, int ol, Graphics2D g) {
		g.setColor(color2);
	    FontMetrics fm = g.getFontMetrics();
	    int x2 = (x - (fm.stringWidth(text)));
		g.drawString(text, ShiftWest(x2, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftWest(x2, ol), ShiftSouth(y, ol));
		g.drawString(text, ShiftEast(x2, ol), ShiftNorth(y, ol));
		g.drawString(text, ShiftEast(x2, ol), ShiftSouth(y, ol));
		g.setColor(color);
		g.drawString(text, x2, y);
	}
	public void drawTypewrittenString(String text, int x, int y, long millis, Graphics2D g) {
		int i;
		for(i = 1; i <= text.length(); i++){
		    g.drawString(text.substring(0, i), x, y);
		    try{
		        Thread.sleep(millis);
		    }catch(InterruptedException ex){
		        Thread.currentThread().interrupt();
		    }
		}
	}
}
