package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import GameState.GameStateManager;
import Pascal.GamePanel;
import Util.PColor;

public class GuiTextfield extends GuiComponent {
	private Rectangle field;
	public String message = "";
	
	public static ArrayList<GuiTextfield> Textfields = new ArrayList<GuiTextfield>();
	
	private int maxletters;
	private int currentletters;
	private long lastChanged = System.currentTimeMillis();
	private String afterline;
	private String stafterline;
	
	private boolean focused;
	
	private int fx;
	private int fy;
	private int fw;
	private int fh;

	public Color Foreground;
	public Color Background;
	public Color borderBright;
	public Color borderDark;
	public String id;
	
	public Font fontText;
	
	public GuiTextfield(String id) {
		super(id);
		fw = 700;
		fh = 70;
		
		Background = PColor.WHITE;
		Foreground = PColor.BLACK;
		stafterline = "|";
		afterline = stafterline;
		
		borderDark = PColor.DARK_GRAY;
		borderBright = PColor.GRAY;
		
		fontText = new Font("Arial", Font.BOLD, 50);
		Textfields.add(this);
	}
	
	public void setBackground(Color color) {
		Background = color;
	}
	public void setForeground(Color color) {
		Foreground = color;
	}
	public void setFont(Font font) {
		fontText = font;
	}
	public void setBorder(Color colorBright, Color colorDark) {
		borderBright = colorBright;
		borderDark = colorDark;
	}
	public void setWidth(int width) {
		fw = width;
	}
	public void setHeight(int height) {
		fh = height;
	}
	public void setLine(String al) {
		stafterline = al;
	}
	public void setText(String text) {
		message = text;
	}
	public String getText() {
		return message;
	}
	
	public void draw(int x, int y, Graphics2D g) {
		if(isVisible()) {
			int width = fw;
			int height = fh;
			field = new Rectangle();
			Rectangle field_ol = new Rectangle();
			Rectangle field_ol2 = new Rectangle();
			field_ol.setSize(width + 4, height + 4);
			field_ol.setLocation(x - 2, y - 2);
			field_ol2.setSize(width + 2, height + 2);
			field_ol2.setLocation(x - 2, y - 2);
			field.setSize(width, height);
			field.setLocation(x, y);
			
			fx = x;
			fy = y;
			
			
			
			if(isFocused()) {
				g.setColor(borderDark);
				g.fill(field_ol);
				g.draw(field_ol);
				g.setColor(borderBright);
				g.fill(field_ol2);
				g.draw(field_ol2);
				
				g.setColor(Background);

				g.fill(field);
				g.draw(field);
				long estimatedTime = System.currentTimeMillis() - lastChanged;
				
				if (estimatedTime > 500) {
					if (afterline.equalsIgnoreCase(stafterline)) {
						afterline = "";
					}
					else if (afterline.equalsIgnoreCase("")) {
						afterline = stafterline;
					}
					lastChanged = System.currentTimeMillis();
				}
				
				g.setFont(fontText);
				g.setColor(Foreground);
				g.drawString(message + afterline, x + 6, y + 50);
				
				maxletters = width;

			    FontMetrics fm = g.getFontMetrics();
			    fm.stringWidth(message);
			    currentletters = fm.stringWidth(message);
			}
			if(!isFocused()) {
				g.setColor(Background);
				g.fill(field_ol);
				g.draw(field_ol);
				g.setColor(Background);
				g.fill(field_ol2);
				g.draw(field_ol2);
				
				g.setColor(Background);
				g.fill(field);
				g.draw(field);
				
				g.setFont(fontText);
				g.setColor(Foreground);
				g.drawString(message, x + 6, y + 50);
			}
		}
	}
	public void writeText(char k) {
		if(this.isFocused() && isVisible()) {
			if(currentletters < maxletters - 15) {
				message = message + k;
			}
			lastChanged = System.currentTimeMillis();
			afterline = stafterline;
		}
	}
	public void delete(String text) {
		text = text.substring(0, text.length() - 1);
    }
	public boolean isFocused() {
		return this.focused;
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	public boolean containsMouse() {
		int mouseXP = (int)(GamePanel.mouseXP);
		int mouseYP = (int)(GamePanel.mouseYP);
		return mouseXP > fx && mouseXP < fx + fw && mouseYP > fy && mouseYP < fy + fh;
	}
	public void click() {
		if(containsMouse()) {
			setFocused(true);
		} else {
			setFocused(false);
		}
	}
	public void update() {
		if(isVisible()) {
			if (GamePanel.releasedMouse()) {
				if (containsMouse()) {
					setFocused(true);
				}
				if (!containsMouse()) {
					setFocused(false);
				}
			}
		}
	}
	public void checkforkey() {
		//CONTROLS
		if (GamePanel.pressedKey() && isFocused()) {
			int k = GamePanel.keyBeingPressed;
			char c = GamePanel.keyBeingTyped;
			
			if (isFocused()) {
				if (k != KeyEvent.VK_SHIFT && k != KeyEvent.VK_CONTROL && k != KeyEvent.VK_BACK_SPACE && k != KeyEvent.VK_LEFT && k != KeyEvent.VK_RIGHT && k != KeyEvent.VK_UP && k != KeyEvent.VK_DOWN && k != KeyEvent.VK_ESCAPE && k != KeyEvent.VK_ALT && k != KeyEvent.VK_CAPS_LOCK && k != KeyEvent.VK_ALT_GRAPH && k != KeyEvent.VK_ENTER) {
					writeText(c);
				}
				if (k == KeyEvent.VK_BACK_SPACE) {
					if(GameStateManager.isstControlDown()) {
						message = "";
					}
					else {
						if (this.message.length() > 0) {
							this.message = this.message.substring(0, this.message.length() - 1);
						}
					}
				}
				if(k == KeyEvent.VK_ESCAPE) {
					setFocused(false);
				}
				if(k == KeyEvent.VK_ENTER) {
					use();
				}
				if(k == KeyEvent.VK_V) {
					if(GameStateManager.isstControlDown()) {
						try {
							String cp = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
							message = message.substring(0, message.length() - 1);
							message =  message + cp;
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	
}
