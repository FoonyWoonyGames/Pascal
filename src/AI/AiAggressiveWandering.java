package AI;

import java.util.Random;

import Entity.Npc;
import Pascal.GamePanel;

public class AiAggressiveWandering extends Ai {

	Random r;
	int walking = 0;
	
	int min;
	int max;
	
	public AiAggressiveWandering(Npc npc) {
		super(npc);
		title = "npcStill";
		stopFollowingRadius = 50;
		
		min = 400;
		max = 900;
	}
	public void update() {
		super.update();
		if(idle) {
			r = new Random();
			int timetospend = r.nextInt(max-min) + min;
			if(System.currentTimeMillis() - onesec > timetospend) {
				onesec = System.currentTimeMillis();
				if(npc.standingStill) {
					if(walking == 1) {
						npc.setRight(false);
						npc.setLeft(true);
						walking = 2;
					}
					else if(walking == 2) {
						npc.setRight(true);
						npc.setLeft(false);
						walking = 1;
					}
					else {
						npc.setLeft(false);
						npc.setRight(true);
						walking = 1;
					}
				}
				if(!npc.standingStill) {
					npc.setIdle();
					npc.setLeft(false);
					npc.setRight(false);
				}
			}
		}
		
		if(GamePanel.currentPlayer.getx() < npc.getx() + npc.getEyeRadius() && GamePanel.currentPlayer.getx() > npc.getx() - npc.getEyeRadius() && 
				GamePanel.currentPlayer.gety() < npc.gety() + npc.getEyeRadius() && GamePanel.currentPlayer.gety() > npc.gety() - npc.getEyeRadius()) {
			follow(GamePanel.currentPlayer);
		}
		else if (GamePanel.currentPlayer.getx() < npc.getx() - 500 || GamePanel.currentPlayer.getx() > npc.getx() + 500 || 
				GamePanel.currentPlayer.gety() < npc.gety() - 500 || GamePanel.currentPlayer.gety() > npc.gety() + 500){
			stopFollowing();
			goHome();
		}
	}

}
