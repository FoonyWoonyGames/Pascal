package Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import GameState.CharacterCustomization;
import Util.Crash;

public class CharacterManager {

	public static void Save() {
		File dir = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal");
		dir.mkdir();
		try (PrintStream out = new PrintStream(
				new FileOutputStream(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/character.txt"))) {
			String filet = new String();
			filet = "\nhat=" + CharacterCustomization.chosenHat
					+ "\novercoat=" + CharacterCustomization.chosenOvercoat
					+ "\ntop=" + CharacterCustomization.chosenTop
					+ "\nlegs=" + CharacterCustomization.chosenLegs
					+ "\nfeet=" + CharacterCustomization.chosenFeet
					+ "\nglasses=" + CharacterCustomization.chosenGlasses
					+ "\nmask=" + CharacterCustomization.chosenMask
					+ "\nset=" + CharacterCustomization.chosenSet;
			
			String updatedText = filet.replaceAll("\n", System.lineSeparator());
            out.println(updatedText);
            out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void Load() {
		FileInputStream inputStream = null;
		String pathString = System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/character.txt";
		try {
			inputStream = new FileInputStream(pathString);
			Properties prop = new Properties();
			prop.load(inputStream);
			
			CharacterCustomization.chosenHat = Integer.parseInt(prop.getProperty("hat"));
			CharacterCustomization.chosenOvercoat = Integer.parseInt(prop.getProperty("overcoat"));
			CharacterCustomization.chosenTop = Integer.parseInt(prop.getProperty("top"));
			CharacterCustomization.chosenLegs = Integer.parseInt(prop.getProperty("legs"));
			CharacterCustomization.chosenFeet = Integer.parseInt(prop.getProperty("feet"));
			CharacterCustomization.chosenGlasses = Integer.parseInt(prop.getProperty("glasses"));
			CharacterCustomization.chosenMask = Integer.parseInt(prop.getProperty("mask"));
			CharacterCustomization.chosenSet = Integer.parseInt(prop.getProperty("set"));
		}
		catch (Exception e) {
			e.printStackTrace();
			Crash.printReport(e);
		} 
	}
}
