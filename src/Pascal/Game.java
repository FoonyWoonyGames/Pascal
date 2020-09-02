package Pascal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import Account.PHPass;
import Account.User;
import Customization.ClothingRegistry;
import Entity.Player;
import Settings.GameSettings;
import Settings.Language;
import Util.CryptationManager;
import Util.ItemRegistry;
import Util.PTime;
import Util.Sound;



public class Game implements MouseMotionListener, MouseListener {
	

	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	public static String username = "Player";
	public static String username2;
	public static String username3;
	public static String basement;
	public String lastnick;
	public static String version;
	public static JFrame window = new JFrame("Pascal");;
	public static JLabel windowl;
	public static JLabel windowl2;
	public static String fullscreen;
	private static Image logo;
	public static GameSettings settings = new GameSettings();
	public static Language lang = new Language();
	
	public static double mX;
	public static double mY;
	
	public static List<Player> activePlayers;
	public static ItemRegistry itemRegistry;
	public static ClothingRegistry clothingRegistry;
	
	public static boolean showfps;
	

	public static JProgressBar pro;
	
	public static CryptationManager cm;
	public static Update updater;
	public static Sound soundPlayer;
	private static ArrayList<String[]> users;

public static void main(String[] args) {
	if(args.length != 2) {
		System.exit(0);
	}
	else {
		if(!checkLogin(args[0], args[1])) {
			System.exit(0);
		}
		else {
			username = args[0];
		}
	}
	new Game();
}
public Game() {
		fullscreen = "true";
		version = "Alpha 1.0.9";
		init();
		ImageIcon logoicon;
		try {
			logoicon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/pascalicon.png")));
			logo = logoicon.getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		window.setTitle("Pascal " + version);
		try {
			settings.getValues();
			lang.getLang();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		int resx = settings.resolutionx;
		int resy = settings.resolutiony;
		if (settings.fullscreen == 0) {
			try {
				window.setSize(resx, resy);
			} catch (NumberFormatException nfe) {
				window.setSize(900, 600);
			}
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
		}
		else if (settings.fullscreen == 2){
			window.setUndecorated(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);
			device.setFullScreenWindow(window);
		}
		else {
			window.setUndecorated(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);
		}
		window.setVisible(true);
		window.setIconImage(logo);
		
		// TODO ~ Status
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				User.setStatus(username, 0);
			}
		});
		
		JPanel windowp2 = new JPanel();
		JPanel windowp = new JPanel();
		((JFrame) window).setContentPane(new GamePanel());
		((JFrame) window).getContentPane().setLayout(new BorderLayout());
		((JFrame) window).getContentPane().add(windowp, BorderLayout.NORTH);
		((JFrame) window).getContentPane().add(windowp2, BorderLayout.CENTER);
		window.getContentPane().requestFocus();

//		window.pack();
		if (settings.fullscreen == 0) {
			window.setLocationRelativeTo(null);
		}
		ImageIcon loader2 = null;
		try {
			loader2 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/loadGame.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		windowl2 = new JLabel();
		windowl2.setIcon(loader2);
		windowp2.add(windowl2);
		windowp.setBackground(Color.BLACK);
		windowp2.setBackground(Color.BLACK);
		
		class FocusLostListener implements WindowFocusListener {
			  public void windowGainedFocus(WindowEvent e) {
				  GamePanel.setFocus(true);
			  }
			  public void windowLostFocus(WindowEvent e) {
				  if(settings.focus) {
					  GamePanel.setFocus(false);
				  }
			  }
		}


		window.pack();
		window.addWindowFocusListener(new FocusLostListener());
	}
	public void mouseDragged(MouseEvent m) {
	}
	public void mouseMoved(MouseEvent e) {
//		if (e.getX() < 5) {
//			bot.mouseMove(6, e.getY());
//		}
//		System.out.println(e.getX());
//		mX= e.getPoint().getX();
//		mY= e.getPoint().getY();
	}
	public void mouseClicked(MouseEvent m) {
	}
	@Override
	public void mouseEntered(MouseEvent m) {
	}
	@Override
	public void mouseExited(MouseEvent m) {
	}
	@Override
	public void mousePressed(MouseEvent m) {
	}
	@Override
	public void mouseReleased(MouseEvent m) {
//		System.out.println("Old mouse!");
	}
	public void init() {
		cm = new CryptationManager();
		updater = new Update();
		soundPlayer = new Sound();
		itemRegistry = new ItemRegistry();
		clothingRegistry = new ClothingRegistry();
		clothingRegistry.addItems();
		User.setStatus(username, 1);
	}
	public static void postLaunch() {
		updater.checkforUpdates();
	}
	public static boolean checkLogin(String u, String p) {
		PHPass passCrypt = new PHPass(0);
		users = new ArrayList<String[]>();
        try {
                Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ee) {
                ee.printStackTrace();
        }
        try{
        	Connection con = DriverManager.getConnection("jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false","pascalgame_net","jkz83has");
        	Statement st = con.createStatement();
        	ResultSet rs = st.executeQuery("select * from clk_e608fa1daf_wp_users");
        	while(rs.next()) {
             	String[] user = {
             			rs.getString(2),
             			rs.getString(3)
             	};
             	users.add(user);
        	}
        } 
        catch(Exception e) {
        	System.out.println(e.getMessage());
        }

		boolean correct = false;
		for(int i = 0; i < users.size(); i++) {
			if(u.equals((users.get(i)[0])) && passCrypt.CheckPassword(p, users.get(i)[1])) {
				correct = true;
				break;
			}
		}
		return correct;
	}
}

