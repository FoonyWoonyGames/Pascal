package Objective;

import java.util.ArrayList;

import Item.Item;
import Item.ItemPistol;

public class Objective03PascalsPast extends ObjectiveBase {
	public Objective03PascalsPast() {
		id = "0201";
		title = "Pastcal";
		descShort = "Find out what has happened to Evigilant City.";
		descLong = "The News-app mentioned fear in Evigilant City. What happened?\nAsk the citizens of Heimdurnk about it.";
		rewardExp = 0;
		rewardMoney = 10;
		rewardItems = new ArrayList<Item>();
		
		state = 0;
		statesMax = 2;
		rewardItems.add(new ItemPistol());
		
	}
	@Override
	public void nextState() {
		super.nextState();
		if(state == 0) {
			state = 1;
			descShort = "Talk to Thomas in Heimdurnk.";
			descLong = "The News-app mentioned fear in Evigilant City. What happened?\nAsk the citizens of Heimdurnk about it.\n"
					+ "Thomas has offered to walk you to the outskirts of Evigilant City, your hometown.\nTalk to him when you're ready to leave.";
		}
		else if(state == 1) {
			state = 2;
			descShort = "Find some clues.";
			descLong = "The News-app mentioned fear in Evigilant City. What happened?\nAsk the citizens of Heimdurnk about it.\n"
					+ "Thomas has offered to walk you to the outskirts of Evigilant City, your hometown.\nTalk to him when you're ready to leave.\n"
					+ "This place reminds you of something. Find out what it is.";
		}
	}
}
 