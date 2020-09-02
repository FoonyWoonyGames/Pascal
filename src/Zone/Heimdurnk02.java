package Zone;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entity.Door;
import GameState.InGame;
import Npcs.NpcJohnny;
import Npcs.NpcLinda;
import Npcs.NpcThomas01;
import TileMap.TileMap;
import Util.PColor;

public class Heimdurnk02 extends Zone {
	
	private Door door01;
	private Door door02;
	private Door door03;
	private Door door04;
	private Door door05;
	private Door door06;
	private NpcLinda npcLinda;
	private NpcJohnny npcJohnny;
	private NpcThomas01 npcThomas;
	
	public Heimdurnk02() {
		super();
		id = 5;
		nameUnlocalized = "heimdurnk02";
		nameLocalized = "Heimdurnk";
		nameSub = "Interior";
		tileMap = new TileMap(120);
		textureMap = "heimdurnkInterior.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		interior = true;
		startingPoint = new Point(6800, 3020);
		
		door01 = new Door(tileMap);
		door01.setPosition(1570, 3960);
		door01.setLocalizedName("Heimdurnk");
		door01.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
				InGame.player.setPosition(1740, 4580);
			}
		});
		door02 = new Door(tileMap);
		door02.setPosition(3120, 3960);
		door02.setLocalizedName("Heimdurnk");
		door02.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
				InGame.player.setPosition(2940, 4460);
			}
		});
		door03 = new Door(tileMap);
		door03.setPosition(4600, 3960);
		door03.setLocalizedName("Heimdurnk");
		door03.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
				InGame.player.setPosition(5690, 4340);
			}
		});
		door04 = new Door(tileMap);
		door04.setPosition(6780, 3960);
		door04.setLocalizedName("Heimdurnk");
		door04.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
				InGame.player.setPosition(6890, 4340);
			}
		});
		door05 = new Door(tileMap);
		door05.setPosition(8790, 3960);
		door05.setLocalizedName("Heimdurnk");
		door05.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
				InGame.player.setPosition(9650, 4340);
			}
		});
		door06 = new Door(tileMap);
		door06.setPosition(11130, 3960);
		door06.setLocalizedName("Heimdurnk");
		door06.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
				InGame.player.setPosition(11210, 4460);
			}
		});
		npcLinda = new NpcLinda(tileMap);
		npcLinda.setStartPos(1760, 3980);
		
		npcJohnny = new NpcJohnny(tileMap);
		npcJohnny.setStartPos(6630, 3980);
		
		npcThomas = new NpcThomas01(tileMap);
		npcThomas.setStartPos(4300, 3980);
		
		addObject(door01);
		addObject(door02);
		addObject(door03);
		addObject(door04);
		addObject(door05);
		addObject(door06);
		addObject(npcLinda);
		addObject(npcJohnny);
		addObject(npcThomas);
		
		loadTiles();
	}
	public void loadTiles() {
		super.loadTiles();
	}
	public void update() {
		super.update();
	}
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(PColor.BLACK);
		g.fillRect(0, 0, 300, 1080);
		g.fillRect(1620, 0, 300, 1080);
	}
	public void drawOver(Graphics2D g) { 
	}
	public void changeThomas() {
		npcThomas.setState(120);
	}
}
