package Object;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.MapObject;
import GUI.GuiMaster;
import GameState.InGame;
import HUD.HudDialogueSign;
import TileMap.TileMap;
import Util.PAction;
import Util.PColor;

public class Sign extends MapObject {
	private String line1;
	private String line2;
	private String line3;
	private String variation;
	private BufferedImage icon;
	private int type;
	private String[] types = {
			"Standing",
			"Hanging"
	};
	private GuiMaster gui;
	private HudDialogueSign dialogue;
	private PAction onClose;
	
	public static ArrayList<Sign> Signs = new ArrayList<Sign>();
	
	public final static int STANDING = 0;
	public final static int HANGING = 1;

	public Sign(TileMap tm) {
		super(tm);
		type = 0;
		variation = "01";
		gui = new GuiMaster();
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/sign" + types[type] + variation + ".png"));
			cwidth = icon.getWidth();
			cheight = icon.getHeight();
			width = icon.getWidth();
			height = icon.getHeight();
		} catch(Exception e) {
			e.printStackTrace();
		}
		dialogue = new HudDialogueSign("sign" + types[type] + variation);
		dialogue.setText(line1 + "\n" + line2 + "\n" + line3);
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				dialogue.setShowing(false);
			}
		});
		
		Signs.add(this);
	}

	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		int locx = (int)(x + xmap - width / 2);
		int locy = (int)(y + ymap - height / 2);
		if(!variation.equals("03")) {
			g.drawImage(icon, locx, locy, null);
		} else {
			g.drawImage(icon, locx+4, locy, null);
		}
		g.setColor(PColor.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		if(line1 != null) {
			gui.drawCenteredColoredOutlinedString(line1, locx+60, locy+45, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
		}
		if(line2 != null) {
			gui.drawCenteredColoredOutlinedString(line2, locx+60, locy+65, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
		}
		if(line3 != null) {
			gui.drawCenteredColoredOutlinedString(line3, locx+60, locy+85, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
		}
	}
	public void setVariation(String v) {
		variation = v;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/sign" + types[type] + variation + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		fixDialogue();
	}
	public void setType(int t) {
		type = t;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/textures/object/sign" + types[type] + variation + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		fixDialogue();
	}
	public void setText(String l1, String l2, String l3) {
		line1 = l1;
		line2 = l2;
		line3 = l3;
		fixDialogue();
	}
	public void setText(String l) {
		if(l.startsWith("{LINE1}")) {
			line1 = l;
		}
		else if(l.startsWith("{LINE2}")) {
			line2 = l;
		}
		else if(l.startsWith("{LINE3}")) {
			line3 = l;
		}
		else {
			line1 = l;
		}
		fixDialogue();
	}
	public void read() {
		InGame.player.stop();
		dialogue.setShowing(true);
	}
	public void setOnClose(PAction a) {
		onClose = a;
	}
	public void onClose() {
		onClose.execute();
	}
	private void fixDialogue() {
		dialogue = new HudDialogueSign("sign" + types[type] + variation);
		dialogue.setText(line1 + "\n" + line2 + "\n" + line3);
		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				dialogue.setShowing(false);
				if(onClose != null) {
					onClose();
				}
			}
		});
	}
}
