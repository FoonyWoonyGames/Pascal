package AI;

import java.util.Random;

import Entity.Npc;

public class AiPassiveWandering extends Ai {

	Random r;
	int walking = 0;
	
	int min;
	int max;
	public AiPassiveWandering(Npc npc) {
		super(npc);
		title = "npcWandering";
		stopFollowingRadius = 130;
		
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
	}

}
