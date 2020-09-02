package HUD;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GUI.GuiMaster;
import GameState.InGame;
import Util.PAction;
import Util.PColor;

public class HudDialogue {
	private String text;
	protected BufferedImage box;
	private BufferedImage icon;
	private BufferedImage arrow01;
	private BufferedImage arrow02;
	private String iconid = "info";
	protected GuiMaster guimaster = new GuiMaster();
	private String name = "Default";
	protected boolean showing;
	public static boolean somethingShowing;
	private int page;
	protected ArrayList<String> lines;
	private String[] textFixed;
	private PAction action;
	
	public static ArrayList<HudDialogue> Dialogues = new ArrayList<HudDialogue>();
	

	public HudDialogue(String str) {
		Dialogues.add(this);
		text = str;
		lines = new ArrayList<String>();
    	try {
			box = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/dialogue.png"));
			arrow01 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/arrow01.png"));
			arrow02 = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/arrow02.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	long arrowBlink;
	boolean white;
	public void draw(Graphics2D g) {
		if(showing) {
			somethingShowing = true;
			g.drawImage(box, 480, 840, null);
			g.drawImage(icon.getScaledInstance(62, 62, BufferedImage.SCALE_FAST), 492, 854, null);
			g.setColor(PColor.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			if(lines.size() == 1) {
				guimaster.drawColoredString(lines.get(0), 500, 970, PColor.BLACK, g);
			}
			else {
				if(!lines.isEmpty()) {
					guimaster.drawColoredString(lines.get(page), 500, 970, PColor.BLACK, g);
				}
				if(!lines.get(page+1).isEmpty()) {
					guimaster.drawColoredString(lines.get(page+1), 500, 1030, PColor.BLACK, g);
				}
			}
			guimaster.drawColoredOutlinedString(name, 566, 904, PColor.WHITE, PColor.BLACK, 1, g);
			if(System.currentTimeMillis() - arrowBlink > 500) {
				if(white) {
					white = false;
				} else {
					white = true;
				}
				arrowBlink = System.currentTimeMillis();
			}
			if(white) {
				g.drawImage(arrow01, 1350, 1025, 30, 30, null);
				g.setFont(new Font("Arial", Font.BOLD, 20));
				guimaster.drawColoredOutlinedString("Press E", 1350, 1025, PColor.WHITE, PColor.BLACK, 1, g);
			}
			else {
				g.drawImage(arrow02, 1350, 1025, 30, 30, null);
				g.setFont(new Font("Arial", Font.BOLD, 20));
				guimaster.drawColoredOutlinedString("Press E", 1350, 1025, PColor.INTERFACE, PColor.BLACK, 1, g);
			}
		}
	}
	public void setIcon(String str) {
		iconid = str;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/gui/dialogue/faces/" + iconid + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setName(String str) { name = str; }
	public String getName() { return name; }
	public void setText(String str) {
		text = str;
		lines = new ArrayList<String>();
		textFixed = text.split("\n");
		for (int i=0;i < textFixed.length;i++) {
			lines.add(textFixed[i]);
		}
		page = 0;
	}
	public static HudDialogue openDialogue() {
		HudDialogue returner = null;
		for(int i = 0; i < HudDialogue.Dialogues.size(); i++) {
			HudDialogue dial = HudDialogue.Dialogues.get(i);
			if(dial.isShowing()) {
				returner = dial;
				break;
			}
		}
		return returner;
	}
	public String getText() { return text; }
	public void setShowing(boolean b) { 
		showing = b;
		somethingShowing = b;
	}
	public boolean isShowing() { return showing; }
	public void nextPage() {
		page++;
		if(page > lines.size()-1) {
			page = lines.size()-1;
		}
	}
	public void prevPage() {
		page--;
		if(page == -1) {
			page = 0;
		}
	}
	public void setPage(int p) {
		page = p;
		if(page == -1) {
			page = 0;
		}
		if(page > lines.size()-1) {
			page = lines.size()-1;
		}
	}
	public void setUse(PAction a) {
		action = a;
	}
	public void use() {
		InGame.player.setLeft(false);
		InGame.player.setRight(false);
		InGame.player.setJumping(false);
		action.execute();
	}
}
