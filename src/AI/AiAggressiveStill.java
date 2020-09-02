package AI;

import Entity.Npc;
import Pascal.GamePanel;

public class AiAggressiveStill extends Ai {

	public AiAggressiveStill(Npc npc) {
		super(npc);
		title = "npcStill";
		stopFollowingRadius = 50;
	}
	public void update() {
		super.update();
		if(idle) {
			npc.setLeft(false);
			npc.setRight(false);
			npc.setUp(false);
			npc.setJumping(false);
		}
		
		if(GamePanel.currentPlayer.getx() < npc.getx() + npc.getEyeRadius() && GamePanel.currentPlayer.getx() > npc.getx() - npc.getEyeRadius()) {
			follow(GamePanel.currentPlayer);
			if(!npc.isAttacking()) {
				npc.setAttacking(true);
			}
		}
		else if (GamePanel.currentPlayer.getx() < npc.getx() - 500 || GamePanel.currentPlayer.getx() > npc.getx() + 500){
			stopFollowing();
			goHome();
			if(npc.isAttacking()) {
				npc.setAttacking(false);
			}
		}
	}

}
