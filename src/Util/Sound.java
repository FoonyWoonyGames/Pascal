package Util;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import GameState.InGame;

public class Sound {
	Clip clip;
	long clipTime;
	boolean paused;
	FloatControl volume;
	FloatControl balance;
	public void play(String path) {
		String pathFixed = path.replace(".", "/");
		String soundName = ("/sound/" + pathFixed + ".wav");
		AudioInputStream audioInputStream;
		try {
			InputStream audioSrc = getClass().getResourceAsStream(soundName);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			
//			80-(80/(100/VOLUME)) << Volume Control
			volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void playAt(String path, int x, int y) {
		play(path);
		
		if(x > InGame.player.getx()) {
			if(x - InGame.player.getx() > 2500) {
				setPan(1.0F);
			}
			else {
				Float pan = (Float.parseFloat(x + "") - InGame.player.getx()) / 2500.0F;
				setPan(pan);
			}
		}
		else if(x < InGame.player.getx()) {
			if(InGame.player.getx() - x > 2500) {
				setPan(-1.0F);
			}
			else {
				Float pan = -(InGame.player.getx() - Float.parseFloat(x + "")) / 2500.0F;
				setPan(pan);
			}
		}
		else {
			setPan(0.0F);
		}
	}
	public void setVolume(Float f) {
		volume.setValue(f.floatValue());
	}
	public void setPan(Float f) {
		balance = (FloatControl) clip.getControl(FloatControl.Type.PAN);
		balance.setValue(f);
	}
	public void stop() {
		if(clip != null) {
			clip.stop();
			clip.flush();
		}
	}
	public void start() {
		clip.start();
	}
	public void pause() {
		if(clip != null) {
			clipTime = clip.getMicrosecondPosition();
			clip.stop();
			paused = true;
		}
	}
	public void resume() {
		if(clip != null) {
			clip.setMicrosecondPosition(clipTime);
			clip.start();
			paused = false;
		}
	}
	public boolean isPaused() {
		return paused;
	}
	public void loop() {
		if(clip != null && !isRunning() && !isPaused()) {
			clip.setMicrosecondPosition(0);
			clip.start();
		}
	}
	public boolean isRunning() {
		boolean b = false;
		if(clip != null) {
			b = clip.isRunning();
		}
		return b;
	}
	public void screenshot() {
		play("gui.screenshot");
	}
	public void error() {
		play("gui.error");
	}
	public void click() {
		play("gui.select");
	}
	public void quit() {
		play("gui.quit");
	}
	public void pickup() {
		play("event.itemPickup");
	}
}
