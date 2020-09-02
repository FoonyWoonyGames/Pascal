package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JProgressBar;

import Pascal.Game;
import Settings.Controls;
import TileMap.Background;
import Util.Screenshot;

@SuppressWarnings("unused")
public class IntroState extends GameState {

	private Background bg;
	long startTime;
	JProgressBar pro;
	
	public IntroState(GameStateManager gsm) {
		this.gsm = gsm;
		bg = new Background("/textures/background/intro.png", 0);

		pro = new JProgressBar();

		
	}
	
	public void init() {
		startTime = System.currentTimeMillis();
	}

	
	
	public void draw(Graphics2D g) {
		Game.windowl2.setVisible(false);
		bg.draw(g);
	}
	public void update() {
		Game.window.setCursor(Game.window.getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
				"null"));

		long timePassed = System.currentTimeMillis() - startTime;
		
		if(timePassed > 3000) {
			GameStateManager.setState(GameStateManager.MENUSTATE);
		}
	}

	public void introscreen() {

		while (pro.getValue() < 100) {
			try {
				Thread.sleep(3000);
				pro.setValue(pro.getValue() + 100);
				System.out.println(pro.getString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void keyPressed(int k, char c) {
		Controls.pressGlobal(k);
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}



	
}