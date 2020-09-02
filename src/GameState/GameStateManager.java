package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Entity.Player;
import GUI.GuiNotification;
import TileMap.TileMap;

public class GameStateManager {

	public static ArrayList<GameState> gameStates;
	public static int currentState;
	public static int prevState;
	public static TileMap currentTileMap;
	public String introb;
	public boolean ctrl;
	public static boolean st_ctrl;
	public boolean shift;
	public static boolean st_shift;
	public boolean alt;
	public static boolean mousePressed;
	private static boolean paused;
	private static boolean dead;
	private static boolean loc;
	public Player player01;
	
	public static long changedState;

	public static final int MENUSTATE = 0;
	public static final int INGAMESTATE = 1;
	public static final int INTROSTATE = 2;
	public static final int ERROR = 3;
	public static final int CHARACTERSTATE = 4;
	public static final int PLAYONLINESTATE = 5;
	public static final int INGAMELOCALSTATE = 6;
	
	public static ArrayList<GuiNotification> notifications;
	
	public GameStateManager() {
		gameStates = new ArrayList<GameState>();
		currentState = INTROSTATE;
		
		gameStates.add(new Menu(this));
		gameStates.add(new InGame(this));
		gameStates.add(new IntroState(this));
		gameStates.add(new Error(this));
		gameStates.add(new CharacterCustomization(this));
		gameStates.add(new PlayOnline(this));
		gameStates.add(new InGameLocal(this));
		
		player01 = new Player(new TileMap(60));
		notifications = new ArrayList<GuiNotification>();
	}
	
	public static void setState(int state) {
		prevState = currentState;
		currentState = state;
		gameStates.get(currentState).init();
		changedState = System.currentTimeMillis();
		if(state == MENUSTATE) {
			Menu.setState(Menu.STATE_MAIN);
		}
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
		drawGlobal(g);
	}
	
	public void keyPressed (int k, char c) {
		gameStates.get(currentState).keyPressed(k, c);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}	
	public void keyTyped(int k) {
		gameStates.get(currentState).keyTyped(k);
	}
	public void mousePressed(MouseEvent m) {
		gameStates.get(currentState).mousePressed(m);
		mousePressed = true;
	}
	public void mouseReleased(MouseEvent m) {
		gameStates.get(currentState).mouseReleased(m);
		mousePressed = false;
	}
	public boolean isControlDown() {
		return ctrl;
	}
	public static boolean isstControlDown() {
		return st_ctrl;
	}
	public boolean isShiftDown() {
		return shift;
	}
	public static boolean isstShiftDown() {
		return st_shift;
	}
	public boolean isAltDown() {
		return alt;
	}
	public static boolean isPaused() {
		return paused;
	}
	public static void setPaused(boolean b) {
		paused = b;
	}
	public static boolean showDeath() {
		return dead;
	}
	public static void setDeath(boolean b) {
		dead = b;
	}
	public static boolean showPos() {
		return loc;
	}
	public static void setShowPos(boolean b) {
		loc = b;
	}
	public static void drawGlobal(Graphics2D g) {
		for (int i=0;i < notifications.size();i++) {
			notifications.get(i).draw(g);
			if(notifications.get(i).shouldRemove()) {
				notifications.remove(i);
			}
		}
	}
}
