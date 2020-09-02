package Zone;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entity.Door;
import Entity.Npc;
import GameState.InGame;
import HUD.HudDialogue;
import Npcs.NpcMilitary01;
import Npcs.NpcNearvalley;
import Npcs.NpcOldGeorge;
import Object.PopcornCart;
import Object.Sign;
import Pascal.Game;
import TileMap.TileMap;
import Util.PAction;

public class Heimdurnk01 extends Zone {
	private Sign signWelcome;
	private Door door01;
	private Door door02;
	private Door door03;
	private Door door04;
	private Door door05;
	private Door door06;
	private Door doorEvigilantHills;
	private NpcOldGeorge oldGeorge;
	private NpcMilitary01 military01;
	private PopcornCart popcornCart;
	private NpcNearvalley nearvalley;
	private HudDialogue phoneDialogue;
	public Heimdurnk01() {
		super();
		id = 4;
		nameUnlocalized = "heimdurnk01";
		nameLocalized = "Heimdurnk";
		nameSub = "";
		tileMap = new TileMap(120);
		textureMap = "heimdurnk.png";
		tileMap.loadMap("/levels/" + nameUnlocalized + ".map");
		interior = false;
		startingPoint = new Point(420, 4460);
		pathAmbient = "ambient.ambientNature01";
		
		phoneDialogue = new HudDialogue("Heimdurnk phone dialogue");
		phoneDialogue.setIcon("phone");
		phoneDialogue.setName("Phone -- Notifications");
		phoneDialogue.setText("[NEWS] >> Evigilant City still in\nfear | Food more expensive than\never | Indie-game accused of\nracism | More in the NEWS app");
		phoneDialogue.setUse(new PAction() {
			@Override
			public void command() {
				if(phoneState != 2) {
					phoneDialogue.nextPage();
				} else {
					phoneDialogue.setShowing(false);
				}
				phoneState++;
			}
		});

		signWelcome = new Sign(tileMap);
		signWelcome.setText("Welcome to", "Heimdurnk", "");
		signWelcome.setType(Sign.STANDING);
		signWelcome.setPosition(670, 4510);

		door01 = new Door(tileMap);
		door01.setPosition(1860, 4560);
		door01.setLocalizedName("Somebody's House");
		door01.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(5);
				InGame.player.setPosition(1440, 3980);
			}
		});
		
		door02 = new Door(tileMap);
		door02.setPosition(3060, 4440);
		door02.setLocalizedName("Small Shack");
		door02.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(5);
				InGame.player.setPosition(2990, 3980);
			}
		});
		
		door03 = new Door(tileMap);
		door03.setPosition(5820, 4320);
		door03.setLocalizedName("Somebody's House");
		door03.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(5);
				InGame.player.setPosition(4480, 3980);
			}
		});
		
		door04 = new Door(tileMap);
		door04.setPosition(7020, 4320);
		door04.setLocalizedName("Somebody's House");
		door04.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(5);
				InGame.player.setPosition(6650, 3980);
			}
		});
		
		door05 = new Door(tileMap);
		door05.setPosition(9780, 4320);
		door05.setLocalizedName("Somebody's House");
		door05.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(5);
				InGame.player.setPosition(8880, 3980);
			}
		});
		
		door06 = new Door(tileMap);
		door06.setPosition(11340, 4440);
		door06.setLocalizedName("Small Shack");
		door06.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(5);
				InGame.player.setPosition(11000, 3980);
			}
		});
		
		doorEvigilantHills = new Door(tileMap);
		doorEvigilantHills.setPosition(120, 4440);
		doorEvigilantHills.invisible = true;
		doorEvigilantHills.setLocalizedName("Evigilant Hills");
		doorEvigilantHills.setUse(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InGame.setZone(3);
				InGame.player.setPosition(12700, 4580);
			}
		});
		
		oldGeorge = new NpcOldGeorge(tileMap);
		oldGeorge.setStartPos(3440, 4460);
		oldGeorge.setInvincible(false);
		oldGeorge.setAi(Npc.AIPASSIVESTILL);
		oldGeorge.setState(250);
		
		military01 = new NpcMilitary01(tileMap);
		military01.setStartPos(11040, 4460);
		
		nearvalley = new NpcNearvalley(tileMap);
		nearvalley.setStartPos(4240, 4440);
		
		popcornCart = new PopcornCart(tileMap);
		popcornCart.setPosition(4040, 4250);
		
		
		objects.add(signWelcome);
		objects.add(door01);
		objects.add(door02);
		objects.add(door03);
		objects.add(door04);
		objects.add(door05);
		objects.add(door06);
		objects.add(doorEvigilantHills);
		objects.add(oldGeorge);
		objects.add(military01);
		objects.add(nearvalley);
		objects.add(popcornCart);
		
		loadTiles();
	}
	public void init() {
		super.init();
		oldGeorge.setState(250);
	}
	public void loadTiles() {
		super.loadTiles();
	}
	boolean georgeKilled = false;
	int phoneState = 0;
	public void update() {
		super.update();
		if(InGame.player.getx() > 1200 && !InGame.objm.containsObjective("0201") && !InGame.completedObjectives.contains("0201")) {
			phoneDialogue.setShowing(true);
			Game.soundPlayer.play("misc.phone");
			InGame.objm.addObjective("0201");
			InGame.player.stop(); 
		}
		if(!InGame.completedObjectives.contains("0102") && !georgeKilled) {
			oldGeorge.killNoDrop();
			georgeKilled = true;
		}
		if(georgeKilled && InGame.completedObjectives.contains("0102")) {
			oldGeorge.revive();
			georgeKilled = false;
		}
	}
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	public void drawOver(Graphics2D g) { 
	}
}
