package Zone;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Door;
import GUI.GuiNotification;
import GameState.InGame;
import Npcs.NpcLinda;
import Npcs.NpcRebelFirst;
import Npcs.NpcThomas01;
import Object.Sign;
import TileMap.TileMap;
import Util.PAction;
import Util.PColor;
import Util.PImage;

public class EvigilantCity01 extends Zone {
	
	private Sign signCity;
	private NpcThomas01 npcThomas;
	private NpcRebelFirst npcRebel;
	private Sign signGrave01;
	private Sign signGrave02;
	private Sign signGrave03;
	private Sign signGrave04;
	private Sign signGrave05;
	private Sign signGrave06;
	private Sign signGrave07;
	private Sign signGrave08;
	
	public EvigilantCity01() {
		super();
		id = 6;
		nameUnlocalized = "evigilantcity01";
		nameLocalized = "Evigilant City";
		nameSub = "The Outskirts";
		tileMap = new TileMap(120);
		textureMap = "evigilantcity.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		interior = false;
		startingPoint = new Point(6260, 4460);
		pathAmbient = "ambient.ambientNature01";
		try {
			sky = ImageIO.read(getClass().getResourceAsStream("/textures/background/skyNight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadTiles();
		
		signCity = new Sign(tileMap);
		signCity.setText("Evigilant City", "2,5km", "");
		signCity.setPosition(6470, 4410);
		signCity.setVariation("02");
		
		npcThomas = new NpcThomas01(tileMap);
		npcThomas.setStartPos(6604, 4465);
		npcThomas.turnAround(false);
		npcThomas.setState(50);
		
		npcRebel = new NpcRebelFirst(tileMap);
		npcRebel.setStartPos(3300, 4465);
		npcRebel.killNoDrop();
		
		signGrave01 = new Sign(tileMap);
		signGrave01.setVariation("03");
		signGrave01.setText("- Hope -", "- Andrems -", "1977-2012");
		signGrave01.setPosition(3190, 4550);
		
		signGrave02 = new Sign(tileMap);
		signGrave02.setVariation("03");
		signGrave02.setText("- Greg -", "- McDaniels -", "1952-2013");
		signGrave02.setPosition(2680, 4550);
		
		signGrave03 = new Sign(tileMap);
		signGrave03.setVariation("03");
		signGrave03.setText("- Jenny -", "1984 - 2009", "\"Yep.\"");
		signGrave03.setPosition(2550, 4550);
		
		signGrave04 = new Sign(tileMap);
		signGrave04.setVariation("03");
		signGrave04.setText("- Tom A. -", "", "1996-2013");
		signGrave04.setPosition(2300, 4550);
		
		signGrave05 = new Sign(tileMap);
		signGrave05.setVariation("03");
		signGrave05.setText("- Juliet -", "", "2005-2016");
		signGrave05.setPosition(1970, 4550);
		signGrave05.setOnClose(new PAction() {
			@Override
			public void command() {
				if(npcThomas.getState() == 50) {
					InGame.state = 1;
					InGame.player.setUnderskin("player/playerAfter");
					npcThomas.setPosition(3400, 4465);
					npcThomas.getAi().follow(InGame.player);
					npcThomas.setState(100);
					npcThomas.use();
				}
			}
		});
		
		signGrave06 = new Sign(tileMap);
		signGrave06.setVariation("03");
		signGrave06.setText("- Tyler L. -", "Thompson", "1986");
		signGrave06.setPosition(1600, 4550);
		signGrave06.setType(Sign.HANGING);
		
		signGrave07 = new Sign(tileMap);
		signGrave07.setVariation("03");
		signGrave07.setText("Unknown", "", "Who's this?");
		signGrave07.setPosition(1490, 4550);
		
		signGrave08 = new Sign(tileMap);
		signGrave08.setVariation("03");
		signGrave08.setText("Jordan", "", "1947-2001");
		signGrave08.setPosition(1100, 4550);
		
		addObject(signCity);
		addObject(signGrave01);
		addObject(signGrave02);
		addObject(signGrave03);
		addObject(signGrave04);
		addObject(signGrave05);
		addObject(signGrave06);
		addObject(signGrave07);
		addObject(signGrave08);
		addObject(npcThomas);
		addObject(npcRebel);
		
		addWall(6800);
		addWall(1300);
	}
	public void init() {
		npcThomas.setState(50);
		npcThomas.use();
		npcRebel.killNoDrop();
	}
	public void loadTiles() {
		super.loadTiles();
	}
	public void update() {
		super.update();
//		InGame.player.setPosition(npcThomas.getx()+100, npcThomas.gety());
		npcThomas.revive();
		if(npcThomas.getx() < 2250 && npcThomas.getState() == 100) {
			npcThomas.setState(101);
			npcThomas.getAi().stopFollowing();
			npcThomas.setLeft(false);
		}
		if(npcThomas.getState() == 104) {
			npcRebel.revive();
			npcRebel.setState(1);
		}
		if(npcThomas.getx() > InGame.player.getx() - 200 && npcThomas.getState() == 110) {
			npcThomas.setRight(false);
			npcThomas.setState(111);
		}
	}
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	public void drawOver(Graphics2D g) { 
	}
	public void end() {
		npcThomas.setState(110);
		npcThomas.setPosition(InGame.player.getx()-1135, 4220);
		npcThomas.revive();
		npcThomas.use();
		npcThomas.setLeft(false);
		npcThomas.setRight(true);
	}
}
