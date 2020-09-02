package GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import Item.Item;
import Pascal.GamePanel;
import Util.PColor;

public class GuiTooltipItem extends GuiTooltip {
	private Item item;
	private String title;
	private boolean isGlobal;
	private int rank;
	private String description;
	private int descLines;
	private Color rankColor;
	private long cooldown;
	
	private int mX;
	private int mY;
	public GuiTooltipItem(Item item) {
		this.item = item;
		title = item.nameLocalized;
		isGlobal = item.isGlobal();
		rank = item.getRank();
		description = item.getDescription();
		cooldown = item.getCooldown();
		if(rank == 0) {
			rankColor = PColor.ITEM_USELESS;
		}
		if(rank == 1) {
			rankColor = PColor.ITEM_NORMAL;
		}
		if(rank == 2) {
			rankColor = PColor.ITEM_GOOD;
		}
		if(rank == 3) {
			rankColor = PColor.ITEM_LEGENDARY;
		}

		String[] descFixed = description.split("\n");
		for (int i=0;i < descFixed.length;i++) {
			descLines++;
		}
	}
	public void draw(Graphics2D g) {
//		RoundRectangle2D rect;
		mX = (int) GamePanel.mouseX;
		mY = (int) GamePanel.mouseY;
		g.setColor(PColor.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		FontMetrics fm = g.getFontMetrics();
//		if(fm.stringWidth(title) > 140) {
//			rect.setBounds(mX, mY, fm.stringWidth(title) + 10, 45+ descLines*18);
//		}
		if(fm.stringWidth(title) > 280) {
			Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
			g.setComposite(c);
			g.setColor(PColor.GRAY);
			g.fillRoundRect(mX, mY, fm.stringWidth(title) + 10, 110+ descLines*36, 20, 20);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);
			g.setComposite(c);
			g.setColor(PColor.BLACK);
			g.fillRoundRect(mX-10, mY-10, fm.stringWidth(title) + 30, 130+ descLines*36, 20, 20);
		}
		else {
			Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
			g.setComposite(c);
			g.setColor(PColor.GRAY);
			g.fillRoundRect(mX, mY, 300, 110+ descLines*36, 20, 20);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);
			g.setComposite(c);
			g.setColor(PColor.BLACK);
			g.fillRoundRect(mX-10, mY-10, 300 + 20, 130+ descLines*36, 20, 20);
		}
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(c);
		drawColoredString(title, mX+6, mY+40, rankColor, g);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		if(isGlobal) {
			drawColoredString("Global", mX+6, mY+66, PColor.LIGHT_GREEN, g);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		if(cooldown < 1000 && cooldown != 0) {
			drawColoredString("Cooldown: " + cooldown + "ms", mX+6, mY+90, PColor.LIGHT_BLUE, g);
		}
		if(cooldown == 0) {
			drawColoredString("Cooldown: Instant", mX+6, mY+90, PColor.LIGHT_BLUE, g);
		}
		if(cooldown >= 1000 && cooldown < 60000) {
			drawColoredString("Cooldown: " + ((float) cooldown/1000) + "s", mX+6, mY+90, PColor.LIGHT_BLUE, g);
		}
		if(cooldown >= 60000 && cooldown < 3600000) {
			drawColoredString("Cooldown: " + ((float) cooldown/60000) + "m", mX+6, mY+90, PColor.LIGHT_BLUE, g);
		}
		if(cooldown >= 3600000) {
			drawColoredString("Cooldown: " + ((float) cooldown/3600000) + "h", mX+6, mY+90, PColor.LIGHT_BLUE, g);
		}
		if(System.currentTimeMillis()-item.getLastUse() < cooldown) {
			int remaining = (int) (100-(cooldown-(System.currentTimeMillis()-item.getLastUse()))*100/cooldown);
			drawColoredString("(" + remaining + "%)", mX+220, mY+90, PColor.LIGHT_BLUE, g);
		}
		g.setFont(new Font("Arial", Font.ITALIC, 24));
		if(!(description == null)) {
			String[] descFixed = description.split("\n");
			for (int i=0;i < descFixed.length;i++) {
				drawColoredString(descFixed[i], mX+6, mY+130 +i*30, PColor.LIGHT_BLUE, g);
			}
		}
		else {
			g.setFont(new Font("Arial", Font.ITALIC, 24));
			drawColoredString("No item description", mX+6, mY+90, PColor.RED, g);
		}

		for (int i=0;i < TTips.size();i++) {
			TTips.remove(TTips.remove(i));
		}
	}

}
