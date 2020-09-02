package Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import GUI.GuiFrame;
import GameState.CharacterCustomization;
import GameState.Menu;
import Pascal.Game;

public class User {
	public static String getID(String username) {
		ArrayList<String[]> users = new ArrayList<String[]>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
        	ee.printStackTrace();
        }
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false","pascalgame_net","jkz83has");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from clk_e608fa1daf_wp_users");
			while(rs.next()) {
				String[] user = {
						rs.getString(1),
						rs.getString(2)
				};
				users.add(user);
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		String id = null;
		for(int i = 0; i < users.size(); i++) {
			if(username.equals(users.get(i)[1])) {
				id = users.get(i)[0];
				break;
			}
		}
		return id;
	}
	public static String getUsername(String uID) {
		ArrayList<String[]> users = new ArrayList<String[]>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
        	ee.printStackTrace();
        }
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false","pascalgame_net","jkz83has");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from clk_e608fa1daf_wp_users");
			while(rs.next()) {
				String[] user = {
						rs.getString(1),
						rs.getString(2)
				};
				users.add(user);
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		String username = null;
		for(int i = 0; i < users.size(); i++) {
			if(uID.equals(users.get(i)[0])) {
				username = users.get(i)[1];
				break;
			}
		}
		return username;
	}
	public static void getEmail(String username) {
		
	}
	public static void unlockItem(String itemName, String username) {
		if(!hasUnlocked(itemName, username)) {
		    try {
				// create a mysql database connection
		    	String myDriver = "com.mysql.jdbc.Driver";
		    	String myUrl = "jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db";
		    	Class.forName(myDriver);
		    	Connection conn = DriverManager.getConnection(myUrl, "pascalgame_net", "jkz83has");
		    	
		    	
		    	// the mysql insert statement
		    	String query = " insert into " + itemName + " (Username)" + " values (?)";

		    	// create the mysql insert preparedstatement
		    	PreparedStatement preparedStmt = conn.prepareStatement(query);
		    	preparedStmt.setString (1, username);

		    	// execute the preparedstatement
		    	preparedStmt.execute();
			      
		    	conn.close();
			} catch (Exception e) {
				System.err.println("Got an exception!");
				System.err.println(e.getMessage());
			}
		}
	}
	public static boolean hasUnlocked(String itemName, String username) {
		ArrayList<String> users = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
        	ee.printStackTrace();
        }
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false","pascalgame_net","jkz83has");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from " + itemName);
			while(rs.next()) {
				String user = rs.getString(1);
				users.add(user);
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		boolean unlocked = false;
		for(int i = 0; i < users.size(); i++) {
			if(username.equals(users.get(i))) {
				unlocked = true;
				break;
			}
		}
		return unlocked;
	}
	public static void loadCustomizations(String username) {
		ArrayList<String[]> users = new ArrayList<String[]>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
        	ee.printStackTrace();
        }
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false","pascalgame_net","jkz83has");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from clk_customizations");
			while(rs.next()) {
				String[] user = {
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9)
				};
				users.add(user);
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		for(int i = 0; i < users.size(); i++) {
			if(username.equals(users.get(i)[0])) {
				CharacterCustomization.chosenHat = Integer.parseInt(users.get(i)[1]);
				CharacterCustomization.chosenOvercoat = Integer.parseInt(users.get(i)[2]);
				CharacterCustomization.chosenTop = Integer.parseInt(users.get(i)[3]);
				CharacterCustomization.chosenLegs = Integer.parseInt(users.get(i)[4]);
				CharacterCustomization.chosenFeet = Integer.parseInt(users.get(i)[5]);
				CharacterCustomization.chosenGlasses = Integer.parseInt(users.get(i)[6]);
				CharacterCustomization.chosenMask = Integer.parseInt(users.get(i)[7]);
				CharacterCustomization.chosenSet = Integer.parseInt(users.get(i)[8]);
				break;
			}
		}
		
	}
	public static void saveCustomizations(String username, int h, int o, int t, int l, int f, int g, int m, int s) {
	    try {
			// create a mysql database connection
	    	String myDriver = "com.mysql.jdbc.Driver";
	    	String myUrl = "jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db";
	    	Class.forName(myDriver);
	    	Connection conn = DriverManager.getConnection(myUrl, "pascalgame_net", "jkz83has");
	    	
	    	
	    	// the mysql insert statement
	    	String query = " insert into clk_customizations (Username, Hat, Overcoat, Top, Legs, Feet, Glasses, Mask, Outfit)"
	    	+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    	if(hasOutfit(username)) {
	    		query = " UPDATE clk_customizations SET Hat = ?, Overcoat = ?, Top = ?, Legs = ?, Feet = ?, Glasses = ?, Mask = ?, Outfit = ? where Username = ?";
	    	}

	    	// create the mysql insert preparedstatement
	    	PreparedStatement preparedStmt = conn.prepareStatement(query);
	    	preparedStmt.setString (1, String.valueOf(h));
	    	preparedStmt.setString (2, String.valueOf(o));
	    	preparedStmt.setString (3, String.valueOf(t));
	    	preparedStmt.setString (4, String.valueOf(l));
	    	preparedStmt.setString (5, String.valueOf(f));
	    	preparedStmt.setString (6, String.valueOf(g));
	    	preparedStmt.setString (7, String.valueOf(m));
	    	preparedStmt.setString (8, String.valueOf(s));
	    	preparedStmt.setString (9, username);

	    	// execute the preparedstatement

	    	if(!hasOutfit(username)) {
		    	preparedStmt.execute();
	    	} else {
		    	preparedStmt.executeUpdate();
	    	}
		      
	    	conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());

			GuiFrame frame;
			frame = new GuiFrame("customizationerror");
			frame.setTitle("Couldn't save customizations!");
			frame.setText("Your character customizations could not be saved!\nPlease check your internet connection.\nIf this is not the issue, please report this bug.");
			frame.setWidth(1050);
			frame.setHeight(180);
			frame.setPosition(960, 260);
			frame.setVisible(true);
			Menu.displayFrame(frame);
		}
	}
	public static boolean hasOutfit(String username) {
		ArrayList<String> users = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ee) {
        	ee.printStackTrace();
        }
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false","pascalgame_net","jkz83has");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from clk_customizations");
			while(rs.next()) {
				String user = rs.getString(1);
				users.add(user);
			}
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		boolean hasOutfit = false;
		for(int i = 0; i < users.size(); i++) {
			if(username.equals(users.get(i))) {
				hasOutfit = true;
				break;
			}
		}
		return hasOutfit;
	}
	public static void setStatus(String username, int s) {
	    try {
			// create a mysql database connection
	    	String myDriver = "com.mysql.jdbc.Driver";
	    	String myUrl = "jdbc:mysql://mysql.pascalgame.net:3306/pascalgame_net_db"+"?useSSL=false";
	    	Class.forName(myDriver);
	    	Connection conn = DriverManager.getConnection(myUrl, "pascalgame_net", "jkz83has");
	    	
	    	
	    	// the mysql insert statement
	    	String query = " UPDATE clk_e608fa1daf_wp_users SET user_status = ? where ID = ?";

	    	// create the mysql insert preparedstatement
	    	PreparedStatement preparedStmt = conn.prepareStatement(query);
	    	preparedStmt.setString (1, String.valueOf(s));
	    	preparedStmt.setString (2, getID(Game.username));

	    	// execute the preparedstatement
		    preparedStmt.executeUpdate();
		      
	    	conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
