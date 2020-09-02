package Settings;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Container.InventoryLoot;
import Entity.Door;
import Entity.ItemEntity;
import Entity.Npc;
import Entity.Player;
import Entity.PlayerLocal;
import GUI.GuiPause;
import GUI.GuiTextfield;
import GUI.GuiVendor;
import GameState.GameStateManager;
import GameState.InGame;
import GameState.InGameLocal;
import HUD.HudDialogue;
import HUD.HudDialogueOption;
import HUD.HudSplash;
import Item.ItemKey;
import Item.ItemTypeWeapon;
import Object.LootBox;
import Object.Sign;
import Pascal.Cheats;
import Pascal.Game;
import Pascal.GamePanel;
import Storage.SaveManager;
import Util.PColor;
import Util.Screenshot;
import Util.Sound;

public class Controls {
	private static boolean dialogueOpen;
	public static void pressGlobal(int k) {
		for (int i=0;i < GamePanel.buttons.size();i++) {
			GamePanel.buttons.get(i).setHovered(false);
		}
		for (int i=0;i < 2;i++) {
			if(GuiVendor.currentVendor != null) {
				GuiVendor.currentVendor.buttons.get(i).press();
			}
		}
		if(k == KeyEvent.VK_F1 || k == KeyEvent.VK_PRINTSCREEN || k == KeyEvent.VK_F12) {
			Screenshot.click();
		}
		if(!GamePanel.isLoading() && GamePanel.isFocused() && !GamePanel.enterCheat && !dialogueOpen) {
			if(k == KeyEvent.VK_F2 || k == KeyEvent.VK_H) {
				if(GamePanel.showingHud()) {
					GamePanel.hudShow(false);
				}
				else {
					GamePanel.hudShow(true);
				}
			}
			if(k == KeyEvent.VK_F3) {
				if(Game.showfps) {
					Game.showfps = false;
				}
				else {
					Game.showfps = true;
				}
			}
		}
	}
	public static void pressStandard(Player player, int k) {
		if(HudDialogue.somethingShowing) {
			dialogueOpen = true;
		}
		else {
			dialogueOpen = false;
		}
		if(!GamePanel.isLoading() && GamePanel.isFocused() && !GamePanel.enterCheat) {
			if(k == KeyEvent.VK_F5) {
				SaveManager.Save(player, 1);
				new HudSplash("Saved", PColor.TIP);
			}
			if(k == KeyEvent.VK_F6) {
				SaveManager.Load(GamePanel.currentPlayer);
			}
			if(k == KeyEvent.VK_F4 || k == KeyEvent.VK_L) {
				if(GameStateManager.showPos()) {
					GameStateManager.setShowPos(false);
				}
				else {
					GameStateManager.setShowPos(true);
				}
			}
			if(k == KeyEvent.VK_ESCAPE) {
				if(!GameStateManager.isPaused()) {
					player.inventory.openInventory(false);
					GameStateManager.setPaused(true);
				} else {
					GuiPause.setState(GuiPause.STATE_MAIN);
					GameStateManager.setPaused(false);
				}
			}
				if(!GameStateManager.isPaused() && !GameStateManager.showDeath() && !dialogueOpen) {
					if(k == KeyEvent.VK_1) player.inventory.setSelectedHand(0);
					if(k == KeyEvent.VK_2) player.inventory.setSelectedHand(1);
					if(k == KeyEvent.VK_3) player.inventory.setSelectedHand(2);
					if(k == KeyEvent.VK_4) player.inventory.setSelectedHand(3);
					if(k == KeyEvent.VK_5) player.inventory.setSelectedHand(4);
					if(k == KeyEvent.VK_A) player.setLeft(true);
					if(k == KeyEvent.VK_Q) {
						if(player.getHeldItem() != null) {
							if(!player.getHeldItem().onCooldown()) {
								player.getHeldItem().lastUse();
								player.useItem();
							}
						}
						else {
							player.setHitting();
						}
					}
					if(k == KeyEvent.VK_F) ((ItemTypeWeapon) player.getHeldItem()).zoom(true);
					if(k == KeyEvent.VK_LEFT) player.setLeft(true);
					if(k == KeyEvent.VK_D) player.setRight(true);
					if(k == KeyEvent.VK_RIGHT) player.setRight(true);
					if(k == KeyEvent.VK_W) player.setJumping(true);
					if(k == KeyEvent.VK_UP) player.setJumping(true);
					if(k == KeyEvent.VK_S) player.setDown(true);
					if(k == KeyEvent.VK_SPACE) player.setJumping(true);
					if(k == KeyEvent.VK_SHIFT) {
						player.setSprinting(true);
					}
					if(k == 0) {
						Cheats.enter();
					}
					if(k == KeyEvent.VK_E) {
					}
				}
		}
		else if(GamePanel.enterCheat) {
			if(k == KeyEvent.VK_ESCAPE) {
				Cheats.CheatEntered("");
			}
		}
	}
	public static void releaseStandard(Player player, int k) {
		if(HudDialogue.somethingShowing) {
			dialogueOpen = true;
		}
		else {
			dialogueOpen = false;
		}
		if(!GamePanel.isLoading() && GamePanel.isFocused() && !GamePanel.enterCheat && !dialogueOpen) {
			if(k == KeyEvent.VK_A) player.setLeft(false);
			if(k == KeyEvent.VK_LEFT) player.setLeft(false);
			if(k == KeyEvent.VK_D) player.setRight(false);
			if(k == KeyEvent.VK_RIGHT) player.setRight(false);
			if(k == KeyEvent.VK_W) player.setJumping(false);
			if(k == KeyEvent.VK_UP) player.setJumping(false);
			if(k == KeyEvent.VK_S) player.setDown(false);
			if(k == KeyEvent.VK_SPACE) player.setJumping(false);
			if(k == KeyEvent.VK_W) player.setJumping(false);
			if(k == KeyEvent.VK_H) new InventoryLoot().open();
			if(k == KeyEvent.VK_F) ((ItemTypeWeapon) player.getHeldItem()).zoom(false);
			if (k == KeyEvent.VK_E) {
				int interactingType = 0;
				
				// ITEMS
				if(interactingType == 0) {
					for (int i=0;i < ItemEntity.items.size();i++) {
						ItemEntity item = ItemEntity.items.get(i);
						if (item != null && player.intersects(item) && item.getTileMap() == player.getTileMap() && !item.shouldRemove()) {
							player.itemAdd(item.getItem());
							item.pickUp();
							interactingType = 1;
							break;
						}
					}
				}

				// NPCS
				if(interactingType == 0) {
					for (int i=0;i < Npc.Npcs.size();i++) {
						Npc npc = Npc.Npcs.get(i);
						if(player.intersects(npc) && player.getTileMap() == npc.getTileMap()) {
							npc.use();
							interactingType = 2;
							break;
						}
					}
				}
				
				// LOOTBOXES
				if(interactingType == 0) {
					for (int h=0;h < LootBox.Loots.size();h++) {
						LootBox loot = LootBox.Loots.get(h);
						if (loot != null && player.intersects(loot) && player.getTileMap() == loot.getTileMap()) {
							loot.open();
							interactingType = 3;
							break;
						}
					}
				}

				// DOORS
				if(interactingType == 0) {
					for (int h=0;h < Door.Doors.size();h++) {
						Door door = Door.Doors.get(h);
						if (door != null && player.intersects(door) && player.getTileMap() == door.getTileMap()) {
							door.use();
							if(door.isLocked()) {
								for (int j=0;j < ItemKey.keys.size();j++) {
									ItemKey key = ItemKey.keys.get(j);
									if(player.inventory.contains(key) && key.getKeyID().equalsIgnoreCase(door.getID())) {
//										Sound.play("misc.doorUnlocked");
										door.setLocked(false);
									}
									else {
//										Sound.play("misc.doorIslocked");
									}
								}
							}
							else {
//								Sound.play("misc.doorOpen");
							}
							interactingType = 4;
							break;
						}
					}
				}

				// SIGNS
				if(interactingType == 0) {
					for (int h=0;h < Sign.Signs.size();h++) {
						Sign sign = Sign.Signs.get(h);
						if (sign != null && player.intersects(sign) && player.getTileMap() == sign.getTileMap()) {
							sign.read();
							interactingType = 5;
							break;
						}
					}
				}
			}
			if(k == KeyEvent.VK_G) {
				player.itemRemove(player.inventory.getItemFromHand());
			}
			if(k == KeyEvent.VK_R) {
				if(player.getHeldItem() != null && player.getHeldItem().isValidWeapon()) {
					ItemTypeWeapon item = (ItemTypeWeapon) player.getHeldItem();
					item.reload();
				}
			}
			if(k == KeyEvent.VK_I) {
				if(!player.inventory.isOpen()) {
					player.inventory.openInventory(true);
					Sound soundPlayer = new Sound();
					soundPlayer.play("event.bagOpen");
					soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundMisc))));
				}
				else {
					player.inventory.openInventory(false);
					Sound soundPlayer = new Sound();
					soundPlayer.play("event.bagClose");
					soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundMisc))));
				}
			}
			if(k == KeyEvent.VK_SHIFT) {
				player.setSprinting(false);
			}
		}
		else if(dialogueOpen) {
			if(k == KeyEvent.VK_E) {
				HudDialogue.openDialogue().use();
			}
			for(int i = 0; i < HudDialogueOption.DialogueOptions.size(); i++) {
				HudDialogueOption optionpane = HudDialogueOption.DialogueOptions.get(i);
				if(optionpane.isShowing()) {
					int usage = Integer.parseInt(KeyEvent.getKeyText(k));
					optionpane.use(usage);
				}
			}
		}
	}
	
	public static void pressLocal(int k) {
		if(!InGameLocal.player01.isDead()) {
			if(k == KeyEvent.VK_W) {
				InGameLocal.player01.setJumping(true);
			}
			if(k == KeyEvent.VK_A) {
				InGameLocal.player01.setLeft(true);
			}
			if(k == KeyEvent.VK_D) {
				InGameLocal.player01.setRight(true);
			}
			if(k == KeyEvent.VK_Q) {
				InGameLocal.player01.useItem();
			}
			if(k == KeyEvent.VK_E) {
				PlayerLocal player = InGameLocal.player01;
				for (int i=0;i < ItemEntity.items.size();i++) {
					ItemEntity item = ItemEntity.items.get(i);
					if (item != null && player.intersects(item) && item.getTileMap() == player.getTileMap() && !item.shouldRemove()) {
						player.itemAdd(item.getItem());
						item.pickUp();
						break;
					}
				}
			}
			if(k == KeyEvent.VK_G) {
				InGameLocal.player01.itemRemove(InGameLocal.player01.heldItem);
			}
			if(k == KeyEvent.VK_R) {
				if(InGameLocal.player01.heldItem != null & InGameLocal.player01.heldItem.isValidWeapon()) {
					((ItemTypeWeapon) InGameLocal.player01.heldItem).reload();
				}
			}
		}

		if(!InGameLocal.player02.isDead()) {
			if(k == KeyEvent.VK_UP) {
				InGameLocal.player02.setJumping(true);
			}
			if(k == KeyEvent.VK_LEFT) {
				InGameLocal.player02.setLeft(true);
			}
			if(k == KeyEvent.VK_RIGHT) {
				InGameLocal.player02.setRight(true);
			}
			if(k == KeyEvent.VK_CONTROL) {
				InGameLocal.player02.useItem();
			}
			if(k == KeyEvent.VK_DOWN) {
				PlayerLocal player = InGameLocal.player02;
				for (int i=0;i < ItemEntity.items.size();i++) {
					ItemEntity item = ItemEntity.items.get(i);
					if (item != null && player.intersects(item) && item.getTileMap() == player.getTileMap() && !item.shouldRemove()) {
						player.itemAdd(item.getItem());
						item.pickUp();
						break;
					}
				}
			}
			if(k == KeyEvent.VK_ENTER) {
				InGameLocal.player02.itemRemove(InGameLocal.player01.heldItem);
			}
			if(k == KeyEvent.VK_SHIFT) {
				if(InGameLocal.player02.heldItem != null & InGameLocal.player02.heldItem.isValidWeapon()) {
					((ItemTypeWeapon) InGameLocal.player02.heldItem).reload();
				}
			}
		}

		if(InGameLocal.playerAmount == 3 && !InGameLocal.player03.isDead()) {
			if(k == KeyEvent.VK_NUMPAD8) {
				InGameLocal.player03.setJumping(true);
			}
			if(k == KeyEvent.VK_NUMPAD4) {
				InGameLocal.player03.setLeft(true);
			}
			if(k == KeyEvent.VK_NUMPAD6) {
				InGameLocal.player03.setRight(true);
			}
			if(k == KeyEvent.VK_NUMPAD7) {
				InGameLocal.player03.useItem();
			}
			if(k == KeyEvent.VK_NUMPAD9) {
				PlayerLocal player = InGameLocal.player03;
				for (int i=0;i < ItemEntity.items.size();i++) {
					ItemEntity item = ItemEntity.items.get(i);
					if (item != null && player.intersects(item) && item.getTileMap() == player.getTileMap() && !item.shouldRemove()) {
						player.itemAdd(item.getItem());
						item.pickUp();
						break;
					}
				}
			}
			if(k == KeyEvent.VK_NUMPAD3) {
				InGameLocal.player03.itemRemove(InGameLocal.player01.heldItem);
			}
			if(k == KeyEvent.VK_NUMPAD1) {
				if(InGameLocal.player03.heldItem != null & InGameLocal.player03.heldItem.isValidWeapon()) {
					((ItemTypeWeapon) InGameLocal.player03.heldItem).reload();
				}
			}
		}
		
		

		if(k == KeyEvent.VK_F4) {
			if(GameStateManager.showPos()) {
				GameStateManager.setShowPos(false);
			}
			else {
				GameStateManager.setShowPos(true);
			}
		}
		if(k == KeyEvent.VK_ESCAPE) {
			if(!GameStateManager.isPaused()) {
				GameStateManager.setPaused(true);
			}
		}
	}
	public static void releaseLocal(int k) {
		if(k == KeyEvent.VK_W) {
			InGameLocal.player01.setJumping(false);
		}
		if(k == KeyEvent.VK_A) {
			InGameLocal.player01.setLeft(false);
		}
		if(k == KeyEvent.VK_D) {
			InGameLocal.player01.setRight(false);
		}

		if(k == KeyEvent.VK_UP) {
			InGameLocal.player02.setJumping(false);
		}
		if(k == KeyEvent.VK_LEFT) {
			InGameLocal.player02.setLeft(false);
		}
		if(k == KeyEvent.VK_RIGHT) {
			InGameLocal.player02.setRight(false);
		}

		if(k == KeyEvent.VK_NUMPAD8) {
			InGameLocal.player03.setJumping(false);
		}
		if(k == KeyEvent.VK_NUMPAD4) {
			InGameLocal.player03.setLeft(false);
		}
		if(k == KeyEvent.VK_NUMPAD6) {
			InGameLocal.player03.setRight(false);
		}
		
	}
	
	public static void mousepressGlobal(MouseEvent e) {
		
	}
	public static void mousereleaseGlobal(MouseEvent e) {
		if(InGame.holdsItem) {
			if(GamePanel.mouseX < 460 || GamePanel.mouseX > 1360 ||
					GamePanel.mouseY < 200 || GamePanel.mouseY > 880) {
				InGame.heldItem.drop(GamePanel.currentPlayer.getx(), GamePanel.currentPlayer.gety(), GamePanel.currentPlayer.getTileMap());
				InGame.heldItem.playSound();
				GamePanel.currentPlayer.inventory.removeItem(InGame.heldItem);
				InGame.holdsItem = false;
				InGame.heldItem = null;
			}
		}
		for (int i=0;i < GamePanel.buttons.size();i++) {
			GamePanel.buttons.get(i).press();
		}
		for (int i=0;i < GuiTextfield.Textfields.size();i++) {
			GuiTextfield.Textfields.get(i).click();
		}
		if(GuiVendor.currentVendor != null) {
			GuiVendor.currentVendor.click();
		}
		for (int i=0;i < InventoryLoot.openInventories.size();i++) {
			if(InventoryLoot.openInventories.get(i).isOpen()) {
				InventoryLoot.openInventories.get(i).click();
			}
		}
		for (int i=0;i < InventoryLoot.openInventories.size();i++) {
			if(InventoryLoot.openInventories.get(i).isOpen()) {
				InventoryLoot.openInventories.get(i).click();
			}
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(GamePanel.currentPlayer.inventory.isOpen()) {
				for (int i=0;i < GamePanel.currentPlayer.inventory.slots.size();i++) {
					GamePanel.currentPlayer.inventory.slots.get(i).rightClick();
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			if(GamePanel.currentPlayer.inventory.isOpen()) {
				for (int i=0;i < GamePanel.currentPlayer.inventory.slots.size();i++) {
					GamePanel.currentPlayer.inventory.slots.get(i).leftClick();
				}
			}
			else {
				if(InGame.holdsItem) {
					boolean hovered = false;
					Loop:
						for (int i=0;i < GamePanel.currentPlayer.inventory.slots.size();i++) {
							if(GamePanel.currentPlayer.inventory.slots.get(i).isHovered()) {
								hovered = true;
								break Loop;
							}
						}
					if(!hovered) {
						GamePanel.currentPlayer.itemRemove(InGame.heldItem);
						InGame.holdsItem = false;
						InGame.heldItem = null;
					}
				}
			}
		}
		GamePanel.mouseXP = GamePanel.mouseX;
		GamePanel.mouseYP = GamePanel.mouseY;
	}
}
