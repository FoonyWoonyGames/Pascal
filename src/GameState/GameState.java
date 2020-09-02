package GameState;

import java.awt.event.MouseEvent;

public abstract class GameState {
		
	protected GameStateManager gsm;
	
	private String title;
	private boolean zone;
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k, char c);
	public abstract void keyReleased(int k);
	public abstract void keyTyped(int k);
	public abstract void mousePressed(MouseEvent m);
	public abstract void mouseReleased(MouseEvent m);
	public String getTitle() {
		if(!title.isEmpty()) {
			return title;			
		}
		else {
			return "No title, perhaps not a zone?";
		}
	}
	public boolean isZone() {
		return zone;
	}
}
