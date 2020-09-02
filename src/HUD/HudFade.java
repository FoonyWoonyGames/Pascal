package HUD;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Pascal.GamePanel;
import Util.PColor;

public class HudFade {
	public long timer;
	public Color color;
	public Rectangle rect;
	public double speed;
	public HudFade() {
		rect = new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		color = PColor.BLACK;
		speed = 1;
	}
	public void draw(Graphics2D g) {
		long time = System.currentTimeMillis() - timer;
		if(time < 2000/speed) {
			g.setColor(color);
			Composite c = null;
			if(time < 50/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			if(time > 50/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			if(time > 100/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			if(time > 150/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			if(time > 200/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			if(time > 250/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			if(time > 300/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			if(time > 350/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			if(time > 400/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			if(time > 450/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			if(time > 1000/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			if(time > 1050/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
			if(time > 1100/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			if(time > 1150/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
			if(time > 1200/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			if(time > 1250/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			if(time > 1300/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			if(time > 1350/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			if(time > 1400/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			if(time > 1450/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			if(time > 1500/speed) c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
			g.setComposite(c);
			g.fill(rect);
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g.setComposite(c);
		}
	}
	public void fade(Color c, double s) {
		speed = s;
		color = c;
		timer = System.currentTimeMillis();
	}
}
