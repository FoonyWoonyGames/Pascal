package net;

import Pascal.Game;
import Pascal.GamePanel;

import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import GUI.GuiButton;
import GUI.GuiOnline;
import GUI.GuiTextfield;
import GameState.Error;
import GameState.GameStateManager;
import GameState.PlayOnline;

@SuppressWarnings("unused")
public class GameClient implements MouseListener {
	
	private static String maxplayers;
	private static URL whatismyip;
	public static void connect() {

		GameStateManager.currentState = GameStateManager.PLAYONLINESTATE;
		String hostname = GuiOnline.iptojoin;
		int port = 30125;
//		String[] ip = hostname.split(":");
//		if (OnlineState.guit2.message.isEmpty() && ip.length >= 2) {
//			port = Integer.parseInt(ip[1]);
//		}
//		if (!OnlineState.guit2.message.isEmpty() && ip.length == 1) {
//			port = Integer.parseInt(OnlineState.porttojoin);
//		}
//		if (!OnlineState.guit2.message.isEmpty() && ip.length >= 2) {
//			port = Integer.parseInt(ip[1]);
//		}
		try (
				Socket cSocket = new Socket(hostname, port);
				PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
//				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
			) {
			String fromServer;
			String userInput;
			try {
				whatismyip = new URL("http://checkip.amazonaws.com");
				BufferedReader inip = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				String pip = inip.readLine();
				out.println(Game.username + " has connected (IP: " + pip + ")");
			} catch (IOException e1) {
				e1.printStackTrace();
				out.println(Game.username + " has connected");
				out.println("Could not get IP address of " + Game.username);
			}
			
			while ((fromServer = in.readLine()) != null) {
			    if (fromServer.startsWith("(Console): ")) {
			    	PlayOnline.title = fromServer;
					Game.window.toFront();
			    }
			    if (fromServer.startsWith("version=")) {
			    	String serverversion = fromServer.replaceFirst("version=", "");
			    	if (!serverversion.equalsIgnoreCase("NONE")) {
			    		out.println(Game.username + " failed to join (bad version)");
						GameStateManager.setState(GameStateManager.ERROR);
						Error.errortitle = "Connection failed";
						Error.errorexp1 = "The server is running another Pascal version than you (" + serverversion + ")";
						Error.details = "You and the server are not running the same version.";
						break;
			    	}
			    }
			    if (fromServer.startsWith("motd=")) {
			    	String motd = fromServer.replaceFirst("motd=", "");
			    	PlayOnline.title = motd;
			    }
			    if (fromServer.startsWith("bans=")) {
			    	String bans = fromServer.replaceFirst("bans=", "");
			    	if (bans.contains("[" + Game.username + ",") || bans.contains(", " + Game.username + ",") || bans.contains(", " + Game.username + "]") || bans.contains("[" + Game.username + "]")) {
			    		out.println(Game.username + " failed to join (banned)");
						GameStateManager.setState(GameStateManager.ERROR);
						Error.errortitle = "Connection failed";
						Error.errorexp1 = "You are banned from this server";
						Error.details = "A server moderator has banned you from the server.";
						break;
			    	}
			    }
			    if (fromServer.startsWith("kicked")) {
					GameStateManager.setState(GameStateManager.ERROR);
					Error.errortitle = "Connection lost";
					Error.errorexp1 = "You have been kicked from the server";
					Error.details = "A server moderator has kicked you from the server.";
				    if (fromServer.startsWith("kicked:")) {
				    	String reason = fromServer.replaceFirst("kicked: ", "");
						Error.errorexp1 = "You have been kicked from the server: " + reason;
				    }
					Game.window.toFront();
					break;
			    }
			    if(fromServer.startsWith("banned ")) {
			    	if(fromServer.equalsIgnoreCase("banned " + Game.username)) {
						GameStateManager.setState(GameStateManager.ERROR);
						Error.errortitle = "Connection lost";
						Error.errorexp1 = "You have been banned from the server";
						Error.details = "A server moderator has banned you from the server.";
						Game.window.toFront();
						break;
			    	}
			    	if(fromServer.startsWith("banned " + Game.username.toLowerCase() + " ")) {
			    		String reason = fromServer.replaceFirst("banned " + Game.username + " ", "");
						GameStateManager.setState(GameStateManager.ERROR);
						Error.errortitle = "Connection lost";
						Error.errorexp1 = "You have been banned from the server: " + reason;
						Error.details = "A server moderator has banned you from the server.";
						Game.window.toFront();
						break;
			    	}
			    }
			    if (fromServer.startsWith("shutdown")) {
					GameStateManager.setState(GameStateManager.ERROR);
					Error.errortitle = "Connection lost";
					Error.errorexp1 = "Server shut down";
					Error.details = "The server has closed.";
					Game.window.toFront();
					break;
			    }
//			    userInput = stdIn.readLine();
//			    if (!userInput.isEmpty()) {
//					out.println("(" + Game.username + "): ");
//			    }
			    PlayOnline.update2();
			}
			
			out.println(Game.username + " has disconnected");
			cSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			GameStateManager.setState(GameStateManager.ERROR);
			Error.errortitle = "Connection failed";
			Error.errorexp1 = "Unknown host";
			Error.details = "Could not connect. Try checking the IP address for spelling errors.";
		} catch (IOException e) {
			e.printStackTrace();
			GameStateManager.setState(GameStateManager.ERROR);
			Error.errortitle = "Connection failed";
			Error.errorexp1 = "Unknown host";
			Error.details = "Could not connect. Try checking your internet connection.";
			Error.errorexp1 = e.getMessage();
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Hellu");
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}