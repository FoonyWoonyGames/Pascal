package Item;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.MapObject;
import Entity.Projectile;
import Entity.ProjectileThrown;
import GameState.InGame;
import HUD.HudDialoguePaper;
import Item.Item;
import Pascal.GamePanel;
import Projectile.ProjectilePistol;
import TileMap.TileMap;
import Util.PAction;

public class ItemPaperWritten extends Item {
	public HudDialoguePaper dialogue = new HudDialoguePaper("item.paperWritten");
	
	public ItemPaperWritten() {
		super();
		
		nameUnlocalized = "item.paperWritten";
		nameLocalized = "Paper with Text";
		id = 17;
		idText = "paper";
		sound = "item.paper";
		variation = "02";
		rank = USELESS;
		description = "The text is somewhat\nreadable.";
		cooldown = 0;
		
		
		
		weight = 0.5;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/textures/item/" + this.idText + variation + ".png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		setText("This is a note."
				+ "\n\nRemember:"
				+ "\n\n        Buy apples"
				+ "\n        Go to the toilet"
				+ "\n        Change this text"
				+ "\n\n\nPlease do before tomorrow.");
		setSize(40);
		setCentered(false);

		dialogue.setUse(new PAction() {
			@Override
			public void command() {
				dialogue.setShowing(false);
			}
		});
		
	}
	
	public void setText(String str) {
		dialogue.setText(str);
	}
	public void setSize(int s) {
		dialogue.setSize(s);
	}
	public void setCentered(boolean b) {
		dialogue.setCentered(b);
	}

	@Override
	public void onPickup() {
	}

	@Override
	public void onUse() {
		dialogue.setShowing(true);
		playSound();
		InGame.player.stop();
	}

	@Override
	public void onEquip() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHold() {
		// TODO Auto-generated method stub
		
	}
}
