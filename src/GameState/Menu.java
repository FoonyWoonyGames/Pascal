package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Account.User;
import GUI.GuiFrame;
import GUI.GuiLocal;
import GUI.GuiMain;
import GUI.GuiMultiplayer;
import GUI.GuiNotification;
import GUI.GuiOnline;
import GUI.GuiSettings;
import GUI.GuiSettingsAdvanced;
import GUI.GuiSettingsAudio;
import GUI.GuiSettingsGraphics;
import GUI.GuiSettingsSocial;
import GUI.GuiSingleplayer;
import Pascal.Game;
import Settings.Controls;
import Util.PTime;

public class Menu extends GameState {

	private GuiMain main;
	private GuiSingleplayer sp;
	private GuiMultiplayer mp;
	private GuiOnline online;
	private GuiSettings settings;
	private GuiSettingsGraphics graphics;
	private GuiSettingsAudio audio;
	private GuiSettingsAdvanced advanced;
	private GuiSettingsSocial social;
	private GuiLocal local;
	
	public static final int STATE_MAIN = 0;
	public static final int STATE_SINGLEPLAYER = 1;
	public static final int STATE_MULTIPLAYER = 2;
	public static final int STATE_ONLINE = 3;
	public static final int STATE_SETTINGS = 4;
	public static final int STATE_GRAPHICS = 5;
	public static final int STATE_AUDIO = 6;
	public static final int STATE_ADVANCED = 7;
	public static final int STATE_SOCIAL = 8;
	public static final int STATE_LOCAL = 9;
	public static int currentState = 0;
	
	public static ArrayList<GuiFrame> frames;
	
	public Menu(GameStateManager gsm) {
		this.gsm = gsm;
		main = new GuiMain();
		sp = new GuiSingleplayer();
		mp = new GuiMultiplayer();
		online = new GuiOnline();
		settings = new GuiSettings();
		graphics = new GuiSettingsGraphics();
		audio = new GuiSettingsAudio();
		advanced = new GuiSettingsAdvanced();
		social = new GuiSettingsSocial();
		local = new GuiLocal();
		frames = new ArrayList<GuiFrame>();
	}
	
	public void init() {
		Game.window.toFront();
		if(PTime.getMonth() == PTime.MONTH_DECEMBER && !User.hasUnlocked("hatXmas", Game.username)) {
			User.unlockItem("hatXmas", Game.username);
			GuiNotification xmashat = new GuiNotification();
			xmashat.setTitle("Clothing unlocked");
			xmashat.setDescription("You just unlocked a\nCHRISTMAS HAT");
			xmashat.setIcon("gui.icon.clothing");
			GuiNotification.Notify(xmashat);
		}
	}
	public void update() {
		for (int i=0;i < frames.size();i++) {
			frames.get(i).update();
		}
	}
	public void draw(Graphics2D g) {
	    if(currentState == STATE_MAIN) {
		    main.draw(g);
	    } else if(currentState == STATE_SINGLEPLAYER) {
	    	sp.draw(g);
	    } else if(currentState == STATE_MULTIPLAYER) {
	    	mp.draw(g);
	    } else if(currentState == STATE_ONLINE) {
	    	online.draw(g);
	    } else if(currentState == STATE_SETTINGS) {
	    	settings.draw(g);
	    } else if(currentState == STATE_GRAPHICS) {
	    	graphics.draw(g);
	    } else if(currentState == STATE_AUDIO) {
	    	audio.draw(g);
	    } else if(currentState == STATE_ADVANCED) {
	    	advanced.draw(g);
	    } else if(currentState == STATE_SOCIAL) {
	    	social.draw(g);
	    } else if(currentState == STATE_LOCAL) {
	    	local.draw(g);
	    }
		for (int i=0;i < frames.size();i++) {
			frames.get(i).draw(g);
		}

	}
	
	public static void setState(int i) {
		currentState = i;
	}


	public void keyPressed(int k, char c) {
		Controls.pressGlobal(k);
		if(k == KeyEvent.VK_ESCAPE) {
			if(currentState == STATE_SINGLEPLAYER) {
				setState(STATE_MAIN);
		    } else if(currentState == STATE_MULTIPLAYER) {
				setState(STATE_MAIN);
		    } else if(currentState == STATE_ONLINE) {
		    	setState(STATE_MULTIPLAYER);
		    } else if(currentState == STATE_SETTINGS) {
				setState(STATE_MAIN);
		    } else if(currentState == STATE_GRAPHICS || currentState == STATE_AUDIO || currentState == STATE_ADVANCED) {
				setState(STATE_SETTINGS);
		    }
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}
	
	public static void displayFrame(GuiFrame fr) {
		frames.add(fr);
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		Controls.mousereleaseGlobal(m);
	}

	
}