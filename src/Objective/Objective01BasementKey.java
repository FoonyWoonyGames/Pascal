package Objective;

import java.util.ArrayList;

import Item.Item;

public class Objective01BasementKey extends ObjectiveBase {
	public Objective01BasementKey() {
		id = "0101";
		title = "The Key To Success";
		descShort = "Find the key to the basement door.";
		descLong = "The door seems to be locked. Go find the key that unlocks it.";
		rewardExp = 0;
		rewardMoney = 2;
		rewardItems = new ArrayList<Item>();
		
		state = 0;
		statesMax = 1;
		
	}
	@Override
	public void nextState() {
		if(state == 0) {
			state = 1;
			descShort = "Go to the basement door.";
		}
		super.nextState();
	}
}
