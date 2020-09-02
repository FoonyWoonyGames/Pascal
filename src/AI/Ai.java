package AI;

import Entity.MapObject;
import Entity.Mob;
import Entity.Npc;

public abstract class Ai {
	protected Npc npc;
	protected String title;
	protected boolean goinghome;
	protected boolean idle;
	protected boolean following;
	protected boolean standing;
	private Mob thingtofollow;
	protected int stopFollowingRadius;
	
	long onesec;
	
	public Ai(Npc npc) {
		this.npc = npc;
	}
	public Mob getTarget() {
		return thingtofollow;
	}
	public void goHome() {
		if(npc.getStartX() > npc.getx()) {
			npc.setRight(true);
			goinghome = true;
		}
		else if(npc.getStartX() < npc.getx()) {
			npc.setLeft(true);
			goinghome = true;
		}
		else {
			return;
		}
	}
	public void setIdle(boolean b) {
		idle = b;
		onesec = System.currentTimeMillis();
	}
	public void setStanding(boolean b) {
		standing = b;
	}
	public void follow(Mob m) {
		if(!m.invisible) {
			following = true;
			thingtofollow = m;
		}
	}
	public boolean isFollowing(Mob m) {
		boolean b = false;
		if(following && thingtofollow == m) {
			b = true;
		}
		return b;
	}
	public void setSFR(int i) {
		stopFollowingRadius = i;
	}
	public void stopFollowing() {
		following = false;
		thingtofollow = null;
	}
	public String getTitle() {
		return title;
	}
	public void update() {
		if(goinghome && npc.getx() > npc.getStartX() - 50 && npc.getx() < npc.getStartX() + 50) {
			npc.setRight(false);
			npc.setLeft(false);
			goinghome = false;
			setIdle(true);
		}
		if(goinghome) {
			if(npc.getStartX() > npc.getx()) {
				npc.setRight(true);
			}
			else if(npc.getStartX() < npc.getx()) {
				npc.setLeft(true);
			}
		}
		if(following) {
			setIdle(false);
			if(npc.getx() < thingtofollow.getx()) {
				npc.setLeft(false);
				npc.setRight(true);
				npc.turnAround(true);
			}
			else if(npc.getx() > thingtofollow.getx()) {
				npc.setLeft(true);
				npc.setRight(false);
				npc.turnAround(false);
			}
			if(npc.getx() > thingtofollow.getx() - stopFollowingRadius && npc.getx() < thingtofollow.getx() + stopFollowingRadius) {
				npc.setLeft(false);
				npc.setRight(false);
			}
		}
		if(standing) {
			setIdle(false);
			goinghome = false;
			following = false;
			npc.setLeft(false);
			npc.setRight(false);
		}
		if(!following && !goinghome && !idle && !standing) {
			setIdle(true);
		}
	}
	public void setStopDistance(int d) {
		stopFollowingRadius = d;
	}
}
