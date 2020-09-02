package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import GameState.GameStateManager;
import GameState.Menu;
import Util.PAction;
import Util.PColor;

public class GuiFrame extends GuiMaster {

	public static ArrayList<GuiFrame> Frames = new ArrayList<GuiFrame>();
	
	private String id;
	private Color foreground;
	private Color background;
	private Color border;
	
	private int width;
	private int height;
	private int x;
	private int y;
	private int bordersize;
	
	private String text;
	private String title;
	private boolean exitable;
	private boolean visible;
	
	private ArrayList<GuiComponent> components;
	private GuiMaster guimaster = new GuiMaster();
	private GuiButton buttonClose = new GuiButton("frameClose");
	// A lot of problems with added components
	public GuiFrame(String str) {
		Frames.add(this);
		
		id = str;
		foreground = PColor.BLACK;
		background = PColor.LIGHT_GRAY;
		border = PColor.DARK_GRAY;
		
		width = 600;
		height = 400;
		x = 400;
		y = 100;
		bordersize = 6;
		
		title = "GuiFrame";
		exitable = true;
		visible = true;
		
		buttonClose.setType(GuiButton.TYPE_CROSS);
		buttonClose.setTitle("");
		buttonClose.gsm(GameStateManager.currentState);
		buttonClose.setUse(new PAction() {
			@Override
			public void command() {
				setVisible(false);
				if(Menu.frames.contains(this)) {
					Menu.frames.remove(this);
				}
			}
		});
		components = new ArrayList<GuiComponent>();
	}
	public void update() {
		if(visible) {
			for (int i=0;i < components.size();i++) {
				components.get(i).update();
				components.get(i).setVisible(true);
			}
			if(exitable) {
				buttonClose.update();
			}
		}
		else {
			for (int i=0;i < components.size();i++) {
				components.get(i).setVisible(false);
				components.remove(i);
			}
		}
	}
	public void draw(Graphics2D g) {
		if(visible) {
			g.setColor(border);
			g.fillRoundRect(x-bordersize, y-bordersize, width+bordersize*2, height+bordersize*2, 30, 30);
			g.setColor(background);
			g.fillRoundRect(x, y, width, height, 30, 30);
			g.setColor(PColor.WHITE);
			g.drawRoundRect(x, y, width, height, 30, 30);
			g.setColor(foreground);
			g.setFont(new Font("Arial", Font.BOLD, 60));
			guimaster.drawCenteredColoredOutlinedString(title, x+width/2, y+50, PColor.INTERFACE, PColor.BLACK, 1, g);
			g.setFont(new Font("Arial", Font.PLAIN, 36));
			String[] textFixed = text.split("\n");
			for (int i=0;i < textFixed.length;i++) {
				guimaster.drawCenteredColoredOutlinedString(textFixed[i], x+width/2, y+90 + i*33, PColor.WHITE, PColor.BLACK, 1, g);
			}
			if(exitable) {
				buttonClose.draw(x+width-40, y+4, g);
			}
		}
	}
	public String getID() {
		return id;
	}
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setPosition(int x, int y) {
		this.x = x-(getWidth()/2);
		this.y = y;
	}
	public void setBackground(Color color) {
		background = color;
	}
	public void setForeground(Color color) {
		foreground = color;
	}
	public void setBorder(Color color) {
		border = color;
	}
	public void setBorderSize(int size) {
		bordersize = size;
	}
	public void setTitle(String str) {
		title = str;
	}
	public String getTitle() {
		return title;
	}
	public void setExitable(boolean b) {
		exitable = b;
	}
	public void setVisible(boolean b) {
		visible = b;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setText(String str) {
		text = str;
	}
	public String getText() {
		return text;
	}
	public void add(GuiComponent component) {
		components.add(component);
		component.addedtoFrame = true;
	}
	public void remove(GuiComponent component) {
		components.remove(component);
		component.addedtoFrame = false;
	}
}
