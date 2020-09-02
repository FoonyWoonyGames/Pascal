package Pascal;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.IllegalFormatConversionException;

import Item.Item;
import Object.Sign;
import Entity.Player;
import Exception.ItemNotFoundException;
import GUI.GuiMaster;
import GUI.GuiNotification;
import GUI.GuiScreen;
import GUI.GuiTextfield;
import GameState.GameStateManager;
import GameState.InGame;
import HUD.HudSplash;
import Util.ItemRegistry;
import Util.PAction;
import Util.PColor;

public class Cheats {
	
	public static GuiTextfield cheatfield;
	
	private static GuiScreen guis = new GuiScreen();
	private static GuiMaster guim = new GuiMaster();
	public static void draw(Graphics2D g) {
		guis.drawBackground(g);
		cheatfield.draw(340, 480, g);
		cheatfield.update();
		g.setFont(new Font("Arial", Font.BOLD, 110));
		guim.drawCenteredColoredOutlinedString("Enter code", 960, 280, PColor.WHITE, PColor.BLACK, 1, g);
	}
	public static void enter() {
		GamePanel.enterCheat = true;
		cheatfield = new GuiTextfield("cheat");
		cheatfield.setFocused(true);
		cheatfield.setWidth(1200);
		cheatfield.setUse(new PAction() {
			@Override
			public void command() {
				CheatEntered(cheatfield.getText());
				GamePanel.enterCheat = false;
			}
		});
		GameStateManager.setPaused(false);
	}
	public static void CheatEntered(String cheat) {
		cheatfield.setFocused(false);
		String[] arg = cheat.split(" ");
		if(cheat.equalsIgnoreCase("fps")) {
			if(Game.showfps) {
				Game.showfps = true;
			}
			else {
				Game.showfps = false;
			}
		}
		if(arg[0].equalsIgnoreCase("superjump")) {
			Player player = GamePanel.currentPlayer;
			player.Superjump(true);
			new HudSplash("Superjump activated", PColor.TIP);
		}
		if(arg[0].equalsIgnoreCase("invisible")) {
			Player player = GamePanel.currentPlayer;
			if(!player.invisible) {
				player.invisible = true;
				new HudSplash("Invisibility activated", PColor.TIP);
			}
			else {
				player.invisible = false;
				new HudSplash("Invisibility deactivated", PColor.TIP);
			}
		}
		if(arg[0].equalsIgnoreCase("god")) {
			Player player = GamePanel.currentPlayer;
			if(!player.isInvincible()) {
				player.invincible = true;
				new HudSplash("Godmode activated", PColor.TIP);
			}
			else {
				player.invincible = false;
				new HudSplash("Godmode deactivated", PColor.TIP);
			}
		}
		if(arg[0].equalsIgnoreCase("flash")) {
			Player player = GamePanel.currentPlayer;
			player.Flash(true);
			new HudSplash("Superun activated", PColor.TIP);
		}
		if(arg[0].equalsIgnoreCase("teleport")) {
			try {
				int x = Integer.parseInt(arg[1]);
				int y = Integer.parseInt(arg[2]);
				Player player = GamePanel.currentPlayer;
				player.setPosition(x, y);
				new HudSplash("Teleported to " + arg[1] + ", " + arg[2], PColor.TIP);
			}
			catch (IllegalFormatConversionException e) {
				new HudSplash(arg[1] + ", " + arg[2] + " is not a valid location", PColor.ERROR);
			}
		}
		if(arg[0].equalsIgnoreCase("setzone")) {
			try {
				InGame.setZone(Integer.parseInt(arg[1]));
				new HudSplash("Changed zone to " + arg[1], PColor.TIP);
			} catch (IndexOutOfBoundsException e) {
				new HudSplash(arg[1] + " is not a valid zone", PColor.ERROR);
			}
		}
		if(arg[0].equalsIgnoreCase("settiles")) {
			try {
				InGame.getZone().getTilemap().loadTiles("/textures/tilesets/" + arg[1] + ".png");
			} catch(Exception e) {
				
			}
		}
		if(arg[0].equalsIgnoreCase("vendor")) {
			Game.itemRegistry.getVendor().setVisible(true);
		}
		if(arg[0].equalsIgnoreCase("give")) {
			Game.itemRegistry = new ItemRegistry();
			int item = Integer.parseInt(arg[1]);
			Player player = GamePanel.currentPlayer;
			try {
				if(Game.itemRegistry.getItem(item) == null || item == 1337) {
					throw new ItemNotFoundException();
				}
				player.itemAdd(Game.itemRegistry.getItem(item));
				new HudSplash("Gave player a " + Game.itemRegistry.getItem(item).getLocalizedName(), PColor.TIP);
			} catch (ItemNotFoundException e) {
				new HudSplash(arg[1] + " is not a valid item ID", PColor.ERROR);
				e.printStackTrace();
			}
		}
		if(arg[0].equalsIgnoreCase("notification")) {
			GuiNotification notification = new GuiNotification();
			notification.setTitle("Notifcation");
			notification.setDescription("This is a notification!\nHello world!");
			notification.setIcon("gui.icon.questionmark");
			GuiNotification.Notify(notification);
		}
		if(arg[0].equalsIgnoreCase("fade")) {
			InGame.hudFade.fade(PColor.BLACK, 2);
		}
		if(arg[0].equalsIgnoreCase("objective.add")) {
			InGame.objm.addObjective(arg[1]);
		}
		if(arg[0].equalsIgnoreCase("objective.complete")) {
			InGame.objm.getActiveObjective(InGame.objm.currentObj).complete();
		}
		if(arg[0].equalsIgnoreCase("objective.fail")) {
			InGame.objm.getActiveObjective(InGame.objm.currentObj).fail();
		}
		if(arg[0].equalsIgnoreCase("objective.setstate")) {
			InGame.objm.getActiveObjective(InGame.objm.currentObj).setState(Integer.parseInt(arg[1])-1);
		}
		if(arg[0].equalsIgnoreCase("spawn")) {
			Game.itemRegistry = new ItemRegistry();
			int item = Integer.parseInt(arg[1]);
			Player player = GamePanel.currentPlayer;
			try {
				Item i = Game.itemRegistry.getItem(item);
				if(i == null || item == 1337) {
					throw new ItemNotFoundException();
				}
				i.drop(player.getx(), player.gety(), player.getTileMap());
				new HudSplash("Spawned a " + Game.itemRegistry.getItem(item).getLocalizedName(), PColor.TIP);
				Game.soundPlayer.play(i.getSound());
			}
			catch(ItemNotFoundException e) {
				e.printStackTrace();
				new HudSplash(arg[1] + " is not a valid item ID", PColor.ERROR);
			}
		}
		if(arg[0].equalsIgnoreCase("placesign")) {
			Sign sign;
			sign = new Sign(InGame.getZone().getTilemap());
			sign.setPosition(InGame.player.getx(), InGame.player.gety());
		
			String[] quotes = cheat.split("\"");
			if(quotes.length == 7) {
				sign.setText(quotes[1], quotes[3]+"", quotes[5]+"");
				try {
					sign.setType(Integer.parseInt(arg[arg.length-2]));
					sign.setVariation(arg[arg.length-1]);
				} catch (Exception e) {
					
				}
			}
			
			InGame.getZone().addObject(sign);
		}
		if(arg[0].equalsIgnoreCase("cash")) {
			InGame.cash = Integer.parseInt(arg[1]);
		}
		if(arg[0].equalsIgnoreCase("sethealth")) {
			int hp = Integer.parseInt(arg[1]);
			Player player = GamePanel.currentPlayer;
			player.setHealth(hp);
			new HudSplash("Set health to " + arg[1], PColor.TIP);
		}
		if(arg[0].equalsIgnoreCase("hurt")) {
			int hp = Integer.parseInt(arg[1]);
			Player player = GamePanel.currentPlayer;
			player.hurt(hp);
			new HudSplash("Damaged player of " + arg[1] + " HP", PColor.TIP);
		}
		if(arg[0].equalsIgnoreCase("heal")) {
			int hp = Integer.parseInt(arg[1]);
			Player player = GamePanel.currentPlayer;
			player.heal(hp);
			new HudSplash("Healed player " + arg[1] + " HP", PColor.TIP);
		}
		if(arg[0].equalsIgnoreCase("setskin")) {
			Player player = GamePanel.currentPlayer;
			player.setSkin(arg[1]);
			new HudSplash("Set player's skin to " + arg[1], PColor.TIP);
		}
		if(arg[0].equalsIgnoreCase("noclip")) {
			Player player = GamePanel.currentPlayer;
			if (!player.noclip) {
				player.noclip = true;
				new HudSplash("Noclip activated", PColor.TIP);
			}
			else {
				player.noclip = false;
				new HudSplash("Noclip deactivated", PColor.TIP);
			}
		}
		if(arg[0].equalsIgnoreCase("fly")) {
			Player player = GamePanel.currentPlayer;
			if (!player.flying) {
				player.flying = true;
				new HudSplash("Flying activated", PColor.TIP);
			}
			else {
				player.flying = false;
				new HudSplash("Flying deactivated", PColor.TIP);
			}
		}
		if(arg[0].equalsIgnoreCase("sound")) {
			try {
				Game.soundPlayer.play(arg[1]);
				new HudSplash("Played soundfile: " + arg[1], PColor.TIP);
			} catch (Exception e) {
				new HudSplash(arg[1] + " is not a valid soundfile", PColor.ERROR);
			}
		}
	}
}
