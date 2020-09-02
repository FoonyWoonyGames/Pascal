package GUI;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import Pascal.GamePanel;
import Util.PAction;
import Util.PColor;

public class GuiSlider extends GuiScreen {

	protected int value;
	protected boolean disabled;
	protected boolean showValue;
	
	protected BufferedImage sliderE;
	protected BufferedImage sliderF;
	protected BufferedImage sliderD;
	protected BufferedImage button;
	protected BufferedImage buttonH;
	protected BufferedImage buttonD;
	
	protected boolean hovered;
	protected boolean pressed;
	
	private PAction action;
	public GuiSlider() {
		value = 0;
		disabled = false;
	    try {
	        button = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/slider/button.png"));
	        buttonH = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/slider/buttonHovered.png"));
	        buttonD = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/slider/buttonDisabled.png"));

	        sliderE = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/slider/sliderEmpty.png"));
	        sliderF = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/slider/sliderFull.png"));
	        sliderD = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/slider/sliderDisabled.png"));
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	public void draw(int x, int y, Graphics2D g) {
		if(value > 100) {
			value = 100;
		}
		if (value < 0) {
			value = 0;
		}
		if(!disabled) {
			if(GamePanel.mouseX > x - sliderE.getWidth()/2 && GamePanel.mouseY > y && GamePanel.mouseX < x + sliderE.getWidth()/2 && GamePanel.mouseY < y + sliderE.getHeight()) {
				hovered = true;
			}
			else {
				hovered = false;
			}
			g.drawImage(sliderE, x - sliderE.getWidth() / 2, y, null);
			g.drawImage(
					sliderF.getSubimage(0, 0, 3 + value * 5, sliderE.getHeight()),
					x - sliderE.getWidth() / 2, y, null);
			if(!hovered) {
				g.drawImage(button, x - (sliderE.getWidth() / 2) + 3 + (value * 5) - (button.getWidth() / 2), y - 6, null);
			}
			else {
				g.drawImage(buttonH, x - (sliderE.getWidth() / 2) + 3 + (value * 5) - (button.getWidth() / 2), y - 6, null);
			}

			if(GameStateManager.mousePressed && hovered) {
				pressed = true;
			}
//			else if(!GameStateManager.mousePressed) {
			else {
				pressed = false;
			}
			if(pressed) {
				value = (int) ((GamePanel.mouseX - (x - sliderE.getWidth() /2)) / 5);
				if(value > 100) {
					value = 100;
				}
				if (value < 0) {
					value = 0;
				}
				if(action != null) action.execute();
			}
		}
		else {
			g.drawImage(sliderD, x - sliderD.getWidth() / 2, y, null);
			g.drawImage(buttonD, x - (sliderD.getWidth() / 2) + 3 + (value * 5) - (buttonD.getWidth() / 2), y - 6, null);
		}
		if(showsValue()) {
			g.setFont(new Font("Arial", Font.BOLD, 20));
			guim.drawCenteredColoredOutlinedString("(" + value + ")", x, y + 40, PColor.WHITE, PColor.BLACK, 1, g);
		}
	}
	public void showValue(boolean b) {
		showValue = b;
	}
	public boolean showsValue() {
		return showValue;
	}
	public void setValue(int v) {
		value = v;
	}
	public int getValue() {
		return value;
	}
	public void setDisabled(boolean b) {
		disabled = b;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setValueListener(PAction a) {
		action = a;
	}
}
