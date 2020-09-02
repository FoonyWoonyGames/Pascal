package Objective;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ObjectiveManager {
	public int currentObj;
	private ArrayList<ObjectiveBase> activeObjs;
	private ArrayList<ObjectiveBase> registry;
	public ObjectiveManager() {
		registry = new ArrayList<ObjectiveBase>();
		registerObjectives();
		currentObj = 0;
		activeObjs = new ArrayList<ObjectiveBase>();
		
	}
	
	public void setObjective(int i) {
		currentObj = i;
	}
	public void addObjective(String id) {
		for (int i=0;i < registry.size();i++) {
			if(registry.get(i).getID().equals(id)) {
				if(noObjective()) {
					currentObj = 0;
				}
				ObjectiveBase obj = registry.get(i);
				activeObjs.add(obj);
				obj.init();
			}
		}
	}
	public void addObjectiveSilent(String id) {
		for (int i=0;i < registry.size();i++) {
			if(registry.get(i).getID().equals(id)) {
				if(noObjective()) {
					currentObj = 0;
				}
				ObjectiveBase obj = registry.get(i);
				activeObjs.add(obj);
			}
		}
	}
	public void removeObjective(String id) {
		for (int i=0;i < activeObjs.size();i++) {
			if(activeObjs.get(i).getID().equals(id)) {
				if(getActiveObjective(currentObj).getID().equals(id)) {
					currentObj = 0;
				}
				activeObjs.remove(activeObjs.get(i));
			}
		}
	}
	public boolean noObjective() {
		return (activeObjs.isEmpty());
	}
	public void RegisterObjective(ObjectiveBase o) {
		registry.add(o);
	}
	public ObjectiveBase getObjectiveFromRegistry(String j) {
		ObjectiveBase objective = null;
		for (int i=0;i < registry.size();i++) {
			if(registry.get(i).getID().equals(j)) {
				objective = registry.get(i);
			}
		}
		return objective;
	}
	public ObjectiveBase getObjective(String j) {
		ObjectiveBase objective = null;
		for (int i=0;i < activeObjs.size();i++) {
			if(activeObjs.get(i).getID().equals(j)) {
				objective = activeObjs.get(i);
			}
		}
		return objective;
	}
	public boolean containsObjective(String id) {
		boolean b = false;
		for (int i=0;i < activeObjs.size();i++) {
			if(activeObjs.get(i).getID().equals(id)) {
				b = true;
			}
		}
		return b;
	}
	public ObjectiveBase getActiveObjective(int i) {
		return activeObjs.get(i);
	}
	public ObjectiveBase getActiveObjective(String id) {
		ObjectiveBase obj = null;
		for (int i=0;i < activeObjs.size();i++) {
			if(activeObjs.get(i).getID().equals(id)) {
				obj = activeObjs.get(i);
			}
		}
		return obj;
	}
	public int getActiveObjectives() {
		return activeObjs.size();
	}
	
	
	public void draw(Graphics2D g) {
		for (int i=0;i < activeObjs.size();i++) {
			activeObjs.get(i).draw(g);
		}
	}
	
	// Register objectives here
	private void registerObjectives() {
		RegisterObjective(new Objective01BasementKey());
		RegisterObjective(new Objective02FirstTown());
		RegisterObjective(new Objective03PascalsPast());
	}
}
