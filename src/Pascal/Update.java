package Pascal;

import GUI.GuiFrame;
import GameState.Menu;
import Util.Updater;

public class Update {
	public void checkforUpdates() {
		int versionid = 1090;
		try {
			if (Integer.parseInt(Updater.getLatestVersion()) > versionid) {
				GuiFrame frame;
				frame = new GuiFrame("newupdate");
				frame.setTitle("New update");
				frame.setText("Your Pascal is not up to date!"
						+ "\nYou can download the newest update in the launcher."
						+ "\nYour version: " + Game.version
						+ "\nNew version: " + Updater.getString());
				frame.setWidth(900);
				frame.setHeight(220);
				frame.setPosition(960, 260);
				frame.setVisible(true);
				Menu.displayFrame(frame);
				
			} else if (versionid != Integer.parseInt(Updater.getLatestVersion())) {
				GuiFrame frame;
				frame = new GuiFrame("nonregisteredversion");
				frame.setTitle("Non-registered version");
				frame.setText("You are running a non-registered version"
						+ "\nof Pascal."
						+ "\nYour version: " + Game.version
						+ "\nLatest version: " + Updater.getString());
				frame.setWidth(860);
				frame.setHeight(220);
				frame.setPosition(960, 260);
				frame.setVisible(true);
				Menu.displayFrame(frame);
			}
		} catch (Exception e) {
		}
	} 
}
