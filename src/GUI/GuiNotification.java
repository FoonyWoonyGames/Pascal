package GUI;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import Pascal.Game;
import Util.PColor;

public class GuiNotification extends GuiScreen {
	
	private BufferedImage background;
	private String title;
	private String description;
	private BufferedImage icon;
	private long time;
	private boolean remove;
	
	public GuiNotification() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/textures/gui/widgets/notification.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		title = "No title.";
		description = "No description.";
		time = System.currentTimeMillis();
		setIcon("gui.dialogue.faces.info");
		remove = false;
	}
	int x = 1920; // 1390 - 1920
	public void draw(Graphics2D g) {
		long timePassed = System.currentTimeMillis() - time;
		if(timePassed < 2900) {
			g.drawImage(background, x, 10, null);
			g.drawImage(icon, x+430, 54, 70, 70, null);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			guim.drawColoredOutlinedString(title.toUpperCase(), x + 20, 60, PColor.INTERFACE, PColor.BLACK, 2, g);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			String[] description = this.description.split("\n");
			for (int i=0;i < description.length;i++) {
				guim.drawColoredOutlinedString(description[i], x + 20, 90 + (26 * i), PColor.WHITE, PColor.BLACK, 1, g);
			}
			if(timePassed <= 400 && x > 1390) {
				x = x - 50;
			}
			else if(timePassed > 400 && timePassed < 2500) {
				// Do nothing here.
			}
			else if(timePassed >= 2500 && x < 1920) {
				x = x + 50;
				if(x >= 1920) {
					remove = true;
				}
			}
		}
	}
	public void setTitle(String str) {
		title = str;
	}
	public String getTitle() {
		return title;
	}
	public void setDescription(String str) {
		description = str;
	}
	public String getDescription() {
		return description;
	}
	public void setIcon(String str) {
		String icon = str.replace(".", "/");
		try {
			this.icon = ImageIO.read(getClass().getResourceAsStream("/textures/" + icon + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean shouldRemove() {
		return remove;
	}
	
	public static void Notify(GuiNotification not) {
		GameStateManager.notifications.add(not);
		Game.soundPlayer.play("misc.pop");
	}
}
