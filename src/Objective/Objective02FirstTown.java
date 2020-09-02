package Objective;

import java.util.ArrayList;

import Item.Item;
import Item.ItemPistol;

public class Objective02FirstTown extends ObjectiveBase {
	public Objective02FirstTown() {
		id = "0102";
		title = "An Old Man In Need";
		descShort = "Walk with the old man to Heimdurnk.";
		descLong = "The old man is scared to walk alone. Help him get to Heimdurnk.";
		rewardExp = 0;
		rewardMoney = 10;
		rewardItems = new ArrayList<Item>();
		
		state = 0;
		statesMax = 0;
		rewardItems.add(new ItemPistol());
		
	}
	@Override
	public void nextState() {
		super.nextState();
	}
}
