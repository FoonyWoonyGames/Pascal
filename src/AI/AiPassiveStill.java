package AI;

import Entity.Npc;

public class AiPassiveStill extends Ai {

	public AiPassiveStill(Npc npc) {
		super(npc);
		title = "npcStill";
		stopFollowingRadius = 130;
	}
	public void update() {
		super.update();
//		if(idle) {
//			npc.setLeft(false);
//			npc.setRight(false);
//			npc.setUp(false);
//			npc.setJumping(false);
//		}
	}

}
