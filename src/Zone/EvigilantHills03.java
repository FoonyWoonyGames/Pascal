package Zone;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entity.Door;
import GameState.InGame;
import Npcs.NpcOldGeorge;
import TileMap.TileMap;

public class EvigilantHills03 extends Zone {

	public NpcOldGeorge oldGeorge;
	private Door doorBasement;
	private Door doorHeimdurnk;
	public EvigilantHills03() {
		super();
		id = 3;
		nameUnlocalized = "evigilanthills03";
		nameLocalized = "Evigilant Hills";
		nameSub = "";
		tileMap = new TileMap(120);
		textureMap = "evigilanthills.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		interior = false;
		startingPoint = new Point(300, 4220);
		pathAmbient = "ambient.ambientNature01";
		
		oldGeorge = new NpcOldGeorge(tileMap);
		oldGeorge.setStartPos(7200, 4100);
		
		doorHeimdurnk = new Door(tileMap);
		doorHeimdurnk.invisible = true;
		doorHeimdurnk.setPosition(12950, 4560);
		doorHeimdurnk.setLocalizedName("Heimdurnk");
		doorHeimdurnk.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(4);
			}
		});
		doorBasement = new Door(tileMap);
		doorBasement.setPosition(180, 4200);
		doorBasement.setLocalizedName("Basement");
		doorBasement.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(2);
				InGame.player.setPosition(3180, 4340);
			}
		});
		
		objects.add(oldGeorge);
		objects.add(doorHeimdurnk);
		objects.add(doorBasement);
		
		loadTiles();
	}
	public void loadTiles() {
		super.loadTiles();
	}
	boolean walked = false;
	public void update() {
		super.update();
		if(InGame.completedObjectives.contains("0102") && oldGeorge.notOnScreen()) {
			oldGeorge.killNoDrop();
		}
		if(InGame.objm.containsObjective("0102") && oldGeorge.getx() > 12000 && !walked) {
			oldGeorge.getAi().setSFR(60);
			oldGeorge.setState(200);
			oldGeorge.use();
			walked = true;
		}
	}
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	public void drawOver(Graphics2D g) { 
	}
}
