package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import Pascal.Game;
import Pascal.GamePanel;
import Util.PAction;
import Util.PColor;

public class GuiScrollable {
	
	public static ArrayList<GuiScrollable> Scrollables = new ArrayList<GuiScrollable>();

	private PAction paint;
	private BufferedImage image;
	private Graphics2D g2;
	
	private int width;
	private int height;
	private int maxHeight;
	private int maxWidth;
	private int x;
	private int y;
	
	private int locationX;
	private int locationY;
	
	private int xProcent;
	private int yProcent;
	
	private boolean disabled;
	private boolean hovered;
	private boolean beingDrawn;
	private boolean buttonHovered;
	private boolean buttonPressed;
	private BufferedImage button;
	private BufferedImage buttonH;
	private BufferedImage buttonD;
	
	public GuiScrollable(int w, int h, int mw, int mh) {
		Scrollables.add(this);
		image = new BufferedImage(mw, mh, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) image.getGraphics();
		
		width = w;
		height = h;
		maxWidth = mw;
		maxHeight = mh;
		x = 0;
		y = 0;
		
		xProcent = 0;
		yProcent = 0;
		
		hovered = false;
		beingDrawn = false;
	    try {
	        button = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/scrollbar/button.png"));
	        buttonH = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/scrollbar/buttonHovered.png"));
	        buttonD = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/scrollbar/buttonDisabled.png"));
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	public void draw(int x, int y, Graphics2D g) {
		
		locationX = x;
		locationY = y;
		if(!disabled) {
			if(GamePanel.mouseX > x && GamePanel.mouseX < x + width && GamePanel.mouseY > y && GamePanel.mouseY < y + height) {
				setHovered(true);
			} else {
				setHovered(false);
			}
			if(GamePanel.mouseX > x+getWidth() && GamePanel.mouseX < x + getWidth()+20 && 
					GamePanel.mouseY > y+(height*yProcent)/100 - 10 && GamePanel.mouseY < y+(height*yProcent)/100 + 30) {
				buttonHovered = true;
			} else if(!buttonPressed) {
				buttonHovered = false;
			}
			if(GameStateManager.mousePressed && buttonHovered) {
				buttonPressed = true;
			}
			else if(!GameStateManager.mousePressed) {
				buttonPressed = false;
			}
			if(buttonPressed) {
				yProcent = (int) (((GamePanel.mouseY - y)/height)*100);
			}
			if(yProcent > 100) {
				yProcent = 100;
			} else if(yProcent < 0) {
				yProcent = 0;
			}
		}
		this.y = (yProcent*(maxHeight-height))/100;
		if(this.y < 0) {
			this.y = 0;
		} 
		else if(this.y + height > maxHeight) {
			this.y = maxHeight - height;
		}
		g.drawImage(image.getSubimage(this.x, this.y, width, height), x, y, null);
		g.setColor(PColor.BLACK);
		if(height != maxHeight) {
			g.fillRoundRect((x+getWidth()-5), y-15, 32, getHeight()+50, 30, 30);
			g.setColor(new Color(30, 30, 30));
			g.fillRoundRect((x+getWidth()), y-10, 22, getHeight()+40, 20, 20);
			if(!buttonHovered && !buttonPressed && !disabled) {
				g.drawImage(button, x+getWidth()+1, y+((height/100)*yProcent) - 10, null);
			} else if(!disabled) {
				g.drawImage(buttonH, x+getWidth()+1, y+((height/100)*yProcent) - 10, null);
			} else {
				g.drawImage(buttonD, x+getWidth()+1, y+((height/100)*yProcent) - 10, null);
			}
		}
		image = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) image.getGraphics();
		if(!Game.settings.antialias) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}
		else {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}
	}
	public void setPaint(PAction a) {
		paint = a;
	}
	public Graphics2D getGraphics() {
		return g2;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setWidth(int w) {
		width = w;
	}
	public void setHeight(int h) {
		height = h;
	}
	public void setMaxWidth(int w) {
		maxWidth = w;
	}
	public void setMaxHeight(int h) {
		maxHeight = h;
	}
	public void setXProcent(int x) {
		xProcent = x;
	}
	public void setYProcent(int y) {
		yProcent = y;
	}
	public int getLocationX() {
		return locationX;
	}
	public int getLocationY() {
		return locationY;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int getMaxWidth() {
		return this.maxWidth;
	}
	public int getMaxHeight() {
		return this.maxHeight;
	};
	public int getXProcent() {
		return this.xProcent;
	}
	public int getYProcent() {
		return this.yProcent;
	};
	public void setHovered(boolean b) {
		hovered = b;
	}
	public boolean isHovered() {
		return hovered;
	}
	public void setDisabled(boolean b) {
		disabled = b;
	}
	public boolean isDisabled() {
		return disabled;
	}
}
