package Pascal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Entity.Player;
import GUI.GuiButton;
import GUI.GuiMaster;
import GUI.GuiPause;
import GUI.GuiScreen;
import GUI.GuiScrollable;
import GUI.GuiTextfield;
import GUI.GuiTooltip;
import GUI.GuiVendor;
import GameState.GameStateManager;
import GameState.InGame;
import GameState.Menu;
import HUD.HudMaster;
import Item.ItemHealth;
import Item.ItemKey;
import Item.ItemPistol;
import TileMap.TileMap;
import Util.ItemRegistry;
import Util.PColor;
import net.GameClient;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

@SuppressWarnings({ "serial", "unused" })
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, FocusListener, WindowStateListener {
	
	// dimensions
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int SCALE = 1;
	
	//game thread 
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	public static int FPSCounter;
	public static List<GuiButton> buttons = new ArrayList<GuiButton>();
	@SuppressWarnings("rawtypes")
	private Point m = MouseInfo.getPointerInfo().getLocation();
	public static boolean enterCheat;
	private static boolean showhud = true;
	private boolean max;
	
	//image
	private BufferedImage image;
	protected Graphics2D g;
	
	//game state manager
	private GameStateManager gsm;
	
	public static Player currentPlayer;
//	static GameClient socketClient;
//	private GameServer socketServer;
	
	public static boolean loading;
	private static long loadingDot;
	private static boolean focus = true;
	private static long focuschange;
	
	private static Robot bot;
	public static double mouseX;
	public static double mouseY;
	public static boolean mouseHasReleased = false;
	public static boolean keyIsPressed = false;
	public static char keyBeingTyped;
	public static int keyBeingPressed;
	public static double mouseXP;
	public static double mouseYP;
	public static BufferedImage cursor;
	
	public static String loadingStringWhat = "";
	public static String loadingString = "LOADING";
	
	
	
	public GamePanel() {
		super();
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		requestFocus();
		requestFocusInWindow();
		setFocusable(true);
	}
	
	public static boolean isLoading() {
		return loading;
	}
	public static void setLoading(boolean b) {
		loading = b;
		loadingDot = System.currentTimeMillis();
	}
	
	public void addNotify() {

		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseWheelListener(this);
			Game.window.addWindowStateListener(this);
			thread.start();
			
		}
		
		

	}
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();
		
		try {
	        cursor = ImageIO.read(getClass().getResourceAsStream("/textures/gui/cursor/default.png"));
			bot = new Robot();
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}
		Game.postLaunch();
		GameStateManager.setState(GameStateManager.INTROSTATE);
		
	}
	public void run() {
        
		init();
		
		
		long start;
		long elapsed;
		long wait;
		
		// FPS counter
		long lastTimeChecked = System.currentTimeMillis();
		int frames = 0;
		
		//game loop
		
		
		while(running) {
			if(!Game.settings.antialias) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			}
			else {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			}
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			
			
			frames++;
			if(System.currentTimeMillis() - lastTimeChecked > 1000) {
				FPSCounter = frames;
				frames = 0;
				lastTimeChecked = System.currentTimeMillis();
			}
			try {
				Thread.sleep(3);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
 		}
	}
	
	
	private void update() {
		if(Game.settings.cursormode == 0) {
			mouseX = MouseInfo.getPointerInfo().getLocation().getX() - Game.window.getLocation().getX();
			mouseY = MouseInfo.getPointerInfo().getLocation().getY() - Game.window.getLocation().getY();
		}
		if(Game.settings.cursormode == 1) {
			mouseX = (MouseInfo.getPointerInfo().getLocation().getX() - Game.window.getLocation().getX()) * 2;
			mouseY = (MouseInfo.getPointerInfo().getLocation().getY() - Game.window.getLocation().getY()) * 2;
		}
		if(!isLoading() && focus && !enterCheat) {
			gsm.update();
		}
		releaseMouse(false);
		if (Game.window.isFocused()) {
			Game.window.setCursor(Game.window.getToolkit().createCustomCursor(
					new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
					"null"));
		}
		else {
			Game.window.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		}
		if (pressedKey()) {
			pressKey(false);
		}
		if (GameStateManager.currentState == GameStateManager.PLAYONLINESTATE) {
			addKeyListener(this);
		}
		
	}

	private GuiScreen guiscreen = new GuiScreen();
	private GuiMaster guimaster = new GuiMaster();
	private void draw() {
		gsm.draw(g);
		if(isLoading()) {
			guiscreen.drawBackground(g);
			g.setFont(new Font("Arial", Font.BOLD, 90));
			guimaster.drawCenteredColoredOutlinedString(loadingString, 960, 500, PColor.WHITE, PColor.BLACK, 1, g);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			String whattoload = "Loading " + loadingStringWhat + " ...";
			guimaster.drawCenteredColoredOutlinedString(whattoload, 960, 530, PColor.LIGHT_GRAY, PColor.BLACK, 1, g);
		}
		if(enterCheat) {
			Cheats.draw(g);
		}
		if(!focus) {
			guiscreen.drawBackground(g);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			BufferedImage bg = null;
		    try {
		        bg = ImageIO.read(getClass().getResourceAsStream("/textures/hud/focus.png"));
		    } catch (IOException e) {
		    }
		    g.drawImage(bg, 500, 15, null);
		    String focusstring = "Click to focus";
		    Color focuscolor = null;
			if(System.currentTimeMillis() - focuschange < 600) {
				focuscolor = PColor.WHITE;
			}
			else {
				focuscolor = PColor.INTERFACE;
				if(System.currentTimeMillis() - focuschange > 1200) {
					focuschange = System.currentTimeMillis();
				}
			}
			guimaster.drawCenteredColoredOutlinedString(focusstring, 960, 265, focuscolor, PColor.BLACK, 1, g);
		}
		if (GameStateManager.currentState != GameStateManager.INTROSTATE) {
			g.drawImage(cursor, (int) mouseX, (int) mouseY, null);
		}
		if(showingHud()) {
			HudMaster.drawFps(g);
			HudMaster.drawVersion(g);
			GuiTooltip.drawTooltips(g);
		}
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g2.dispose();

	}
	
	public static void hudShow(boolean b) {
		showhud = b;
	}
	public static boolean showingHud() {
		return showhud;
	}

	public static void setFocus(boolean b) {
		focuschange = System.currentTimeMillis();
		focus = b;
	}
	public static boolean isFocused() {
		return focus;
	}
	public void keyTyped(KeyEvent key) {
	}
	public void keyPressed(KeyEvent key) {
		try {
			gsm.keyPressed(key.getKeyCode(), key.getKeyChar());
			gsm.ctrl = key.isControlDown();
			GameStateManager.st_ctrl = key.isControlDown();
			gsm.shift = key.isShiftDown();
			GameStateManager.st_shift = key.isShiftDown();
			gsm.alt = key.isAltDown();
			keyBeingTyped = key.getKeyChar();
			keyBeingPressed = key.getKeyCode();
			pressKey(true);
			for (int i=0;i < GuiTextfield.Textfields.size();i++) {
				GuiTextfield.Textfields.get(i).checkforkey();
			}
		}
		catch(Exception e) {
		}

	}
	public void keyReleased(KeyEvent key) {
		try {
			gsm.keyReleased(key.getKeyCode());
			gsm.ctrl = key.isControlDown();
			GameStateManager.st_ctrl = key.isControlDown();
			gsm.shift = key.isShiftDown();
			GameStateManager.st_shift = key.isShiftDown();
			gsm.alt = key.isAltDown();
		}
		catch(Exception e) {
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		try {
			gsm.mousePressed(e);
		} catch(Exception ex) {
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		try {
			gsm.mouseReleased(e);
		} catch(Exception ex) {}
	}
	public static boolean releasedMouse() {
		return mouseHasReleased;
	}
	public void releaseMouse(boolean b) {
		mouseHasReleased = b;
		return;
	}
	public static boolean pressedKey() {
		return keyIsPressed;
	}
	public void pressKey(boolean b) {
		keyIsPressed = b;
		return;
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void focusGained(FocusEvent e) {
		  GamePanel.setFocus(true);
	}

	@Override
	public void focusLost(FocusEvent e) {
		  GamePanel.setFocus(false);
	}
	public void windowStateChanged(WindowEvent e){
		   if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
			   max = true;
		   }
		   else {
			   max = false;
		   }
		}
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(GameStateManager.currentState == GameStateManager.INGAMESTATE) {
			int change = e.getUnitsToScroll()/3;
			int hand = currentPlayer.inventory.getHand()+change;
			if(hand > 4) {
				hand = 0;
			}
			else if(hand < 0) {
				hand = 4;
			}
			currentPlayer.inventory.setSelectedHand(hand);
		}
		for (int i=0;i < GuiScrollable.Scrollables.size();i++) {
			GuiScrollable scrollable = GuiScrollable.Scrollables.get(i);
			if(scrollable.isHovered() && !scrollable.isDisabled()) {
				scrollable.setYProcent(scrollable.getYProcent() + (2*e.getUnitsToScroll()));
			}
		}
	}
}
