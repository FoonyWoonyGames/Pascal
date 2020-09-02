package GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

import GUI.GuiButton;
import GUI.GuiMaster;
import Settings.Controls;
import TileMap.Background;
import Util.PColor;

public class Error extends GameState {

	private Background bg;
	public static String errorreport;
	public static String errortitle;
	public static String errorexp1;
	public static String details;
	public static boolean Explain;

	GuiButton buttonErrorMenu = new GuiButton("errorback");
	GuiButton buttonErrorDetails = new GuiButton("errordetails");
	GuiMaster guim = new GuiMaster();
	
	public Error(GameStateManager gsm) {
		this.gsm = gsm;
		try {

			bg = new Background("/textures/background/black.png", 1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		errortitle = "ERROR";
		errorexp1 = "No error was specified :/";
		details = "No explanation was specified :/";
		Explain = false;
		buttonErrorMenu.setTitle("Take me to the menu!");
		buttonErrorMenu.gsm(GameStateManager.ERROR);

		buttonErrorDetails.setTitle("Details...");
		buttonErrorDetails.gsm(GameStateManager.ERROR);
		buttonErrorDetails.setDisabled(false);
	}
	public void update() {
		if(Explain) {
			buttonErrorDetails.setDisabled(true);
		}
	}
	
	
	
	
	public void draw(Graphics2D g) {

		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		//draw bg
		bg.draw(g);
		

		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		g.setFont(new Font("Arial", Font.BOLD, 30));
		guim.drawCenteredColoredString(errortitle, 450, 150, PColor.RED, g);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
//		drawCenteredString(errorexp1, 900, 350, g);
		guim.drawCenteredColoredString(errorexp1, 450, 200, PColor.WHITE, g);
		if(Explain) {
			g.setFont(new Font("Arial", Font.ITALIC, 15));
			guim.drawCenteredColoredString(details, 450, 250, PColor.WHITE, g);
		}

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		buttonErrorMenu.draw(330, 320, g);
		buttonErrorDetails.draw(330, 370, g);

	}
	


	public void keyPressed(int k, char c) {
		Controls.pressGlobal(k);
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		Controls.mousereleaseGlobal(m);
	}

}