package HUD;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Door;
import Entity.ItemEntity;
import Entity.Npc;
import Entity.Player;
import GUI.GuiMaster;
import Item.ItemTypeWeapon;
import Object.LootBox;
import Object.Sign;
import Pascal.GamePanel;
import Util.PColor;

public class HudPlayer {
	private Player player;
	private BufferedImage health;
	private BufferedImage health2;
	private BufferedImage healthEmpty;
	private BufferedImage energy;
	private BufferedImage energy2;
	private BufferedImage energyEmpty;
	
	
	
	
	private GuiMaster guim = new GuiMaster();
	public HudPlayer(Player player) {
		this.player = player;
		
		try {
			health = ImageIO.read(getClass().getResourceAsStream("/textures/hud/health.png"));
			health2 = null;
			healthEmpty = ImageIO.read(getClass().getResourceAsStream("/textures/hud/empty.png"));
			energy = ImageIO.read(getClass().getResourceAsStream("/textures/hud/energy.png"));
			energy2 = null;
			energyEmpty = ImageIO.read(getClass().getResourceAsStream("/textures/hud/empty_small.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	long reloadTime;
	Color col = PColor.RED;
	public void draw(Graphics2D g) {
	    g.drawImage(healthEmpty, 660, -20, null);
	    g.drawImage(energyEmpty, 660, 40, null);
	    

	    if(player.getHeldItem() != null && player.getHeldItem().isValidWeapon() && ((ItemTypeWeapon) player.getHeldItem()).getMaxAmmo() != 0) {
			Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g.setComposite(c);
	    	g.setColor(PColor.BLACK);
	    	g.fillRoundRect(45, 964, 160, 80, 40, 40);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
	    	ItemTypeWeapon item = (ItemTypeWeapon) player.getHeldItem();
	    	g.setFont(new Font("Arial", Font.BOLD, 46));
	    	guim.drawCenteredColoredOutlinedString(item.getAmmo()+"", 100, 1020, PColor.WHITE, PColor.BLACK, 1, g);
	    	guim.drawCenteredColoredOutlinedString("/", 136, 1020, PColor.WHITE, PColor.BLACK, 1, g);
	    	g.setFont(new Font("Arial", Font.BOLD, 30));
	    	guim.drawCenteredColoredOutlinedString(""+item.getMaxAmmo(), 160, 1020, PColor.LIGHT_GREY, PColor.BLACK, 1, g);
	    	if(item.getAmmo() == 0) {
	    		if(System.currentTimeMillis() - reloadTime > 500) {
	    			if(col == PColor.RED) {
	    				col = PColor.WHITE;
	    			}
	    			else {
	    				col = PColor.RED;
	    			}
	    			reloadTime = System.currentTimeMillis();
	    		}
		    	g.setFont(new Font("Arial", Font.BOLD, 25));
		    	guim.drawCenteredColoredOutlinedString("RELOAD!", 200, 970, col, PColor.BLACK, 1, g);
	    	}
	    }
		if(player.getEnergy()/50 > 0) {
		   energy2 = energy.getSubimage(0, 0,  (int) (player.getEnergy() / 50 * 6.64), energy.getHeight());
		}

	    if(player.getEnergy()/50 > 0) {
		    g.drawImage(energy2, 794, 124, null);
	    }
	    if(player.getHealth() > 0) {
			g.drawImage(player.getHealthImage(), 708, 50, null); 
	    }
	    

	    // Energy
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		guim.drawCenteredColoredOutlinedString(player.getEnergy()/50 + " / 50", 960, 156, PColor.WHITE, PColor.BLACK, 1, g);
		

		
		// Health
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.setColor(Color.WHITE);
		if(player.dead) {
			guim.drawCenteredColoredOutlinedString("DEAD", 960, 95, PColor.WHITE, PColor.BLACK, 1, g);
		}
		else {
			guim.drawCenteredColoredOutlinedString(player.getHealth() + " / 100", 960, 95, PColor.WHITE, PColor.BLACK, 1, g);
		}
		
		// Items
		int items = 0;
		for (int i=0;i < ItemEntity.items.size();i++) {
			ItemEntity item = ItemEntity.items.get(i);
			if (item != null && player.intersects(item) && item.getTileMap() == player.getTileMap() && !item.shouldRemove()) {
				items++;
			}
		}

	    int interactingType = 0;
		if(!HudDialogue.somethingShowing) {

			for (int i=0;i < ItemEntity.items.size();i++) {
				ItemEntity item = ItemEntity.items.get(i);
				if (item != null && player.intersects(item) && item.getTileMap() == player.getTileMap() && !item.shouldRemove() && interactingType == 0) {
					g.setFont(new Font("Arial", Font.BOLD, 60));
					guim.drawCenteredColoredOutlinedString("PRESS E TO PICK UP", 960, 1020, PColor.WHITE, PColor.BLACK, 1, g);
					g.setFont(new Font("Arial", Font.BOLD, 40));
					guim.drawCenteredColoredOutlinedString(item.getItem().getLocalizedName().toUpperCase(), 960, 1060, PColor.LIGHT_YELLOW, PColor.BLACK, 1, g);
					interactingType = 1;
					break;
				}
			}
			
			for (int i=0;i < Npc.Npcs.size();i++) {
				Npc npc = Npc.Npcs.get(i);
				if (npc != null && npc.intersects(player) && player.getTileMap() == npc.getTileMap() && !npc.isDead() && interactingType == 0) {
					if(GamePanel.showingHud()) {
						g.setFont(new Font("Arial", Font.BOLD, 60));
						guim.drawCenteredColoredOutlinedString("PRESS E TO INTERACT", 960, 1020, PColor.WHITE, PColor.BLACK, 1, g);
						g.setFont(new Font("Arial", Font.BOLD, 40));
						guim.drawCenteredColoredOutlinedString(npc.getLongName().toUpperCase(), 960, 1060, PColor.LIGHT_YELLOW, PColor.BLACK, 1, g);
					}
					interactingType = 2;
					break;
				}
			}
			
			for (int h=0;h < LootBox.Loots.size();h++) {
				LootBox loot = LootBox.Loots.get(h);
				if (player != null && loot.intersects(player) && player.getTileMap() == loot.getTileMap() && interactingType == 0) {
					if(GamePanel.showingHud()) {
						g.setFont(new Font("Arial", Font.BOLD, 50));
						g.setFont(new Font("Arial", Font.BOLD, 60));
						guim.drawCenteredColoredOutlinedString("PRESS E TO OPEN", 960, 1020, PColor.WHITE, PColor.BLACK, 1, g);
						g.setFont(new Font("Arial", Font.BOLD, 40));
						guim.drawCenteredColoredOutlinedString(loot.getName().toUpperCase(), 960, 1060, PColor.LIGHT_YELLOW, PColor.BLACK, 1, g);
					}
					interactingType = 3;
					break;
				}
			}
			
			for (int h=0;h < Door.Doors.size();h++) {
				Door door = Door.Doors.get(h);
				if (player != null && door.intersects(player) && player.getTileMap() == door.getTileMap() && interactingType == 0) {
					if(GamePanel.showingHud()) {
						if(door.isLocked()) {
							g.setFont(new Font("Arial", Font.BOLD, 50));
							guim.drawCenteredColoredOutlinedString("(Locked)", 960, 960, PColor.DARK_RED, PColor.BLACK, 1, g);
							g.setFont(new Font("Arial", Font.BOLD, 60));
							guim.drawCenteredColoredOutlinedString("PRESS E TO UNLOCK", 960, 1020, PColor.WHITE, PColor.BLACK, 1, g);
						}
						else {
							g.setFont(new Font("Arial", Font.BOLD, 60));
							guim.drawCenteredColoredOutlinedString("PRESS E TO ENTER", 960, 1020, PColor.WHITE, PColor.BLACK, 1, g);
							g.setFont(new Font("Arial", Font.BOLD, 40));
							guim.drawCenteredColoredOutlinedString(door.getLocalizedName().toUpperCase(), 960, 1060, PColor.LIGHT_YELLOW, PColor.BLACK, 1, g);
						}
					}
					interactingType = 4;
					break;
				}
			}
			
			for (int h=0;h < Sign.Signs.size();h++) {
				Sign sign = Sign.Signs.get(h);
				if (player != null && sign.intersects(player) && player.getTileMap() == sign.getTileMap() && interactingType == 0) {
					if(GamePanel.showingHud()) {
						g.setFont(new Font("Arial", Font.BOLD, 50));
						guim.drawCenteredColoredOutlinedString("PRESS E TO READ", 960, 960, PColor.WHITE, PColor.BLACK, 1, g);
					}
					interactingType = 5;
					break;
				}
			}

			
		}
		
		// Inventory
		player.inventory.draw(g);
	}
}
