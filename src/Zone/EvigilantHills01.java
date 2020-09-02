package Zone;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entity.Door;
import Entity.Npc;
import GameState.InGame;
import Item.ItemApple;
import Item.ItemBreadMoldy;
import Item.ItemCakePiece;
import Npcs.NpcKeykeeper;
import Object.LootBox;
import Object.Sign;
import TileMap.TileMap;
import TileMap.Water;

public class EvigilantHills01 extends Zone {
	public Npc keykeeper;
	public Door doorCity;
	public Door doorBasement;
	private Sign signTower;

	private Water water01;
	private Water water02;
	public EvigilantHills01() {
		super();
		id = 1;
		nameUnlocalized = "evigilanthills01";
		nameLocalized = "Evigilant Hills";
		tileMap = new TileMap(120);
		textureMap = "evigilanthills.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		interior = false;
		startingPoint = new Point(380, 4580);
		pathAmbient = "ambient.ambientNature01";

		water01 = new Water(9020, 4225, 250, 250);
		water02 = new Water(7820, 3505, 260, 370);

		keykeeper = new NpcKeykeeper(tileMap);
		keykeeper.setStartPos(10130, 1020);

		doorCity = new Door(tileMap);
		doorCity.setPosition(300, 3720);
		doorCity.setLocked(true);

		doorBasement = new Door(tileMap);
		doorBasement.setLocalizedName("Basement");
		doorBasement.setPosition(12900, 4320);
		doorBasement.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(Zone.EVIGILANTHILLS02);
			}
		});
		
		signTower = new Sign(tileMap);
		signTower.setPosition(12110, 4340);
		signTower.setText("Stockut Tower", "", "1632");
		signTower.setType(Sign.HANGING);
		
		LootBox lootCrate = new LootBox(tileMap);
		lootCrate.addItem(new ItemBreadMoldy());
		lootCrate.addItem(new ItemApple());
		lootCrate.addItem(new ItemBreadMoldy());
		lootCrate.setPosition(8870, 4220);
		lootCrate.setName("Old Crate");
		lootCrate.setIcon("crate");
		
//		addWater(water01);
//		addWater(water02);

		addObject(doorCity);
		addObject(doorBasement);
		addObject(keykeeper);
		addObject(signTower);
		addObject(lootCrate);
		
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
