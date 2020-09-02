package Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import javax.naming.ConfigurationException;

import Util.Crash;

public class GameSettings {
	FileInputStream inputStream;
	FileOutputStream fos;
	String result;
	public boolean antialias;
	public boolean shownicknames;
	public boolean clicksounds;
	public int bgquality;
	public boolean shadows;
	public int fullscreen;
	public int resolutionx;
	public int resolutiony;
	public boolean customresolution;
	public String resizable;
	public String language;
	public String memory;
	public boolean focus;
	public boolean autosave;
	public int autosavetime;
	public int cursormode;
	public boolean locationformat;
	public int soundMusic;
	public int soundAmbient;
	public int soundFootsteps;
	public int soundItems;
	public int soundInterface;
	public int soundMisc;
	public boolean fog;
	
	public static final int DIFFICULTY_LIGHT = 1;
	public static final int DIFFICULTY_EASY = 2;
	public static final int DIFFICULTY_NORMAL = 3;
	public static final int DIFFICULTY_HARD = 4;
	public static final int DIFFICULTY_DNI = 5;
	
	public void getValues() throws IOException {
		
		try {
			Properties prop = new Properties();
			String propFileName = "settings.ini";

			inputStream = new FileInputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/settings.ini");
			
			if(inputStream != null) {
				prop.load(inputStream);
			}
			else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath" + inputStream);
			}
			
			// Get values
			antialias = Boolean.parseBoolean(prop.getProperty("antialias"));
			shownicknames = Boolean.parseBoolean(prop.getProperty("shownicknames"));
			clicksounds = Boolean.parseBoolean(prop.getProperty("clicksounds"));
			bgquality = Integer.parseInt(prop.getProperty("bgquality"));
			shadows = Boolean.parseBoolean(prop.getProperty("shadows"));
			fullscreen = Integer.parseInt(prop.getProperty("fullscreen"));
			resolutionx = Integer.parseInt(prop.getProperty("resolutionx"));
			resolutiony = Integer.parseInt(prop.getProperty("resolutiony"));
			customresolution = Boolean.parseBoolean(prop.getProperty("customresolution"));
			resizable = prop.getProperty("resizable");
			language = prop.getProperty("language");
			memory = prop.getProperty("memory");
			focus = Boolean.parseBoolean(prop.getProperty("focusscreen"));
			autosave = Boolean.parseBoolean(prop.getProperty("autosave"));
			autosavetime = Integer.parseInt(prop.getProperty("autosavetime"));
			cursormode = Integer.parseInt(prop.getProperty("cursormode"));
			locationformat = Boolean.parseBoolean(prop.getProperty("locationformat"));
			soundMusic = Integer.parseInt(prop.getProperty("soundMusic"));
			soundAmbient = Integer.parseInt(prop.getProperty("soundAmbient"));
			soundFootsteps = Integer.parseInt(prop.getProperty("soundFootsteps"));
			soundItems = Integer.parseInt(prop.getProperty("soundItems"));
			soundInterface = Integer.parseInt(prop.getProperty("soundInterface"));
			soundMisc = Integer.parseInt(prop.getProperty("soundMisc"));
			fog = Boolean.parseBoolean(prop.getProperty("fog"));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
			restore();
		} finally {
			inputStream.close();
		}
		
	}
	
	public void setValue(String property, String value) throws ConfigurationException, IOException {

		Properties prop = new Properties();

		inputStream = new FileInputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/settings.ini");
		fos = new FileOutputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/settings.ini");
		try {
			
			prop.load(inputStream);
			
			// Set values
			prop.setProperty("antialias", antialias + "");
			prop.setProperty("shownicknames", shownicknames+"");
			prop.setProperty("clicksounds", clicksounds+"");
			prop.setProperty("shadows", shadows+"");
			prop.setProperty("fullscreen", fullscreen+"");
			prop.setProperty("resolutionx", resolutionx+"");
			prop.setProperty("resolutiony", resolutiony+"");
			prop.setProperty("resizable", resizable);
			prop.setProperty("customresolution", customresolution+"");
			prop.setProperty("bgquality", bgquality+"");
			prop.setProperty("language", language);
			prop.setProperty("memory", memory);
			prop.setProperty("focusscreen", focus+"");
			prop.setProperty("autosave", autosave+"");
			prop.setProperty("autosavetime", autosavetime+"");
			prop.setProperty("cursormode", cursormode+"");
			prop.setProperty("locationformat", locationformat+"");
			prop.setProperty("soundMusic", soundMusic+"");
			prop.setProperty("soundAmbient", soundAmbient+"");
			prop.setProperty("soundFootsteps", soundFootsteps+"");
			prop.setProperty("soundItems", soundItems+"");
			prop.setProperty("soundInterface", soundInterface+"");
			prop.setProperty("soundMisc", soundMisc+"");
			prop.setProperty("fog", fog+"");
			prop.setProperty(property, value);
			prop.store(fos, "");
			
			getValues();
		}
		catch (Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
			restore();
		} finally {
			inputStream.close();
			fos.close();
		}
	}
	
	public void toggle(String str) {
		try {
			Properties prop = new Properties();
			
			inputStream = new FileInputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/settings.ini");
			
			if(inputStream != null) {
				prop.load(inputStream);
			}
			
			if(prop.getProperty(str).equalsIgnoreCase("true")) {
				setValue(str, "false");
			}
			else {
				setValue(str, "true");
			}
			getValues();
		} catch(Exception e) {
			e.printStackTrace();
			restore();
		}
	}
	public void restore() {
		try (PrintStream out = new PrintStream(
				new FileOutputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/settings.ini"))) {
			
            out.println("resolutionx=800");
            out.println("resizable=false");
            out.println("focusscreen=true");
            out.println("shadows=true");
            out.println("memory=1024");
            out.println("language=en");
            out.println("autosavetime=60000");
            out.println("clicksounds=true");
            out.println("autosave=true");
            out.println("shownicknames=false");
            out.println("bgquality=1");
            out.println("customresolution=false");
            out.println("fullscreen=1");
            out.println("antialias=true");
            out.println("locationformat=true");
            out.println("cursormode=1");
            out.println("resolutiony=500");
            out.println("soundMusic=100");
            out.println("soundAmbient=100");
            out.println("soundFootsteps=100");
            out.println("soundItems=100");
            out.println("soundInterface=100");
            out.println("soundMisc=100");
            out.println("fog=true");
            out.close();
            
            getValues();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
