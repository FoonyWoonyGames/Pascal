package Settings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import Pascal.Game;
import Util.Crash;

public class Language {
	InputStream inputStream;
	String result;
	public String guiPicknick1;
	public String guiPicknick2;
	public String guiPicknickErr1;
	public String guiPicknickErr2;
	public String guiPicknickErr3;
	public String guiSingleplayer;
	public String guiMultiplayer;
	public String guiSettings;
	public String guiQuit;
	public String guiBack;
	public String guiSingleplayerContinue;
	public String guiSingleplayerNew;
	public String guiSingleplayerLoad;
	public String guiSingleplayerTutorial;
	public String guiPaused;
	public String guiResume;
	public String guiOptions;
	public String guiExit;
	public String guiDied;
	public String guiRespawn;
	
	
	public void getLang() throws IOException {
		
		try {
			Properties prop = new Properties();
			String lang = Game.settings.language;
//			new FileInputStream(
			inputStream = Game.class.getResourceAsStream("/lang/" + lang + ".lang");
			
			if(inputStream != null) {
				prop.load(inputStream);
			}
			else {
				throw new FileNotFoundException("property file '" + lang + "' not found in the classpath" + inputStream);
			}
			
			// Get values
			guiPicknick1 = prop.getProperty("guiPicknick1");
			guiPicknick2 = prop.getProperty("guiPicknick2");
			guiPicknickErr1 = prop.getProperty("guiPicknickErr1");
			guiPicknickErr2 = prop.getProperty("guiPicknickErr2");
			guiPicknickErr3 = prop.getProperty("guiPicknickErr3");
			
			guiSingleplayer = prop.getProperty("guiSingleplayer");
			guiMultiplayer = prop.getProperty("guiMultiplayer");
			guiSettings = prop.getProperty("guiSettings");
			guiQuit = prop.getProperty("guiQuit");
			
			guiBack = prop.getProperty("guiBack");
			
			guiSingleplayerContinue = prop.getProperty("guiSingleplayerContinue");
			guiSingleplayerNew = prop.getProperty("guiSingleplayerNew");
			guiSingleplayerLoad = prop.getProperty("guiSingleplayerLoad");
			guiSingleplayerTutorial = prop.getProperty("guiSingleplayerTutorial");
			
			guiPaused = prop.getProperty("guiPaused");
			guiResume = prop.getProperty("guiResume");
			guiOptions = prop.getProperty("guiOptions");
			guiExit = prop.getProperty("guiExit");
			
			guiDied = prop.getProperty("guiDied");
			guiRespawn = prop.getProperty("guiRespawn");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
		} finally {
			inputStream.close();
		}
		
	}
}
