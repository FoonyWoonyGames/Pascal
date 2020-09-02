package TileMap;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.*;

import Pascal.GamePanel;
import Util.Crash;
import Util.PColor;

public class Background {
	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(String s, double ms) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));    
//			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		    GraphicsDevice device = env.getDefaultScreenDevice();
//		    GraphicsConfiguration config = device.getDefaultConfiguration();
//		    image = PColor.copy(bg);
//		    image = config.createCompatibleImage(1920, 1080, Transparency.TRANSLUCENT);
			setMoveScale(ms);
		}
		catch(Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
		}
	}
	public Background(Color black) {
		return;
	}
	public void setPosition(double x, double y) {
		this.x = (x * getMoveScale()) % GamePanel.WIDTH;
		this.y = (y * getMoveScale()) % GamePanel.HEIGHT;
	}
	public void setVector( double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public void update() {
		x += dx;
		x += dy;
	}
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x,  (int)y, null);
	}
	public double getMoveScale() {
		return moveScale;
	}
	public void setMoveScale(double moveScale) {
		this.moveScale = moveScale;
	}
}
