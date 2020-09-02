package Zone;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entity.Door;
import GameState.Error;
import GameState.GameStateManager;
import GameState.InGame;
import TileMap.TileMap;

public class EvigilantHills02 extends Zone {

	public Door doorEnterance;
	public Door doorExit;
	public Door doorSecret;
	
	public EvigilantHills02() {
		super();
		id = 2;
		nameUnlocalized = "evigilanthills02";
		nameLocalized = "Evigilant Hills";
		nameSub = "Basement";
		tileMap = new TileMap(120);
		textureMap = "evigilanthills.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		interior = true;
		startingPoint = new Point(300, 4100);

		doorEnterance = new Door(tileMap);
		doorEnterance.setLocalizedName("Evigilant Hills");
		doorEnterance.setPosition(180, 4080);
		doorEnterance.setUse(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(1);
				InGame.player.setPosition(12770, 4320);
			}
			
		});
		doorExit = new Door(tileMap);
		doorExit.setLocalizedName("Evigilant Hills");
		doorExit.setLocked(true);
		doorExit.setPosition(3300, 4320);
		doorExit.setUse(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(doorExit.isLocked()) {
					if(!InGame.objm.containsObjective("0101")) {
						InGame.objm.addObjective("0101");
					}
				}
				else {
					if(InGame.objm.containsObjective("0101")) {
						InGame.objm.getObjective("0101").complete();
					}
					InGame.setZone(3);
				}
			}
		});

		doorSecret = new Door(tileMap);
		doorSecret.setLocked(true);
		doorSecret.setPosition(3780, 4320);
		doorSecret.setUse(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStateManager.setState(GameStateManager.ERROR);
				Error.errortitle = "YOU FOUND AN EASTER-EGG";
				Error.errorexp1 = "Congratulations.";
				Error.details = "110 101 119 32 116 97 108 101";
			}
		});
		doorExit.setID("basementExit");
		
		addObject(doorEnterance);
		addObject(doorExit);
		addObject(doorSecret);
		
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
	}
}
