package Util;

public abstract class PAction {
	public abstract void command();
	
	public void execute() {
		command();
	}
}
