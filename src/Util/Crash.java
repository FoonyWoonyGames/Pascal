package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Pascal.Game;

public class Crash {
	public static void printReport(Exception report) {
		File dir = new File("../errorlogs");
		dir.mkdir();
		
		GregorianCalendar c = new GregorianCalendar();
		String currentDate = c.get(Calendar.YEAR)+"_" + c.get(Calendar.MONTH) +"_"+c.get(Calendar.DATE)+"_"+c.get(Calendar.HOUR)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND);
		
		
		try (PrintStream out = new PrintStream(new FileOutputStream("../errorlogs/log_" + currentDate + ".txt"))) {
			String filet = new String();
			StringWriter errors = new StringWriter();
			report.printStackTrace(new PrintWriter(errors));
			errors.toString();
			filet = "It looks like your game crashed! Luckily we got a report!\n\n"
					+ "--------------------------------------------------------------------\n"
					+ "System information:\n\n"
					+ "Operating system: " + System.getProperty("os.name") + "\n"
					+ "Operating system version: " + System.getProperty("os.version") + "\n"
					+ "Language: " + System.getProperty("user.language") + "\n"
					+ "User name: " + System.getProperty("user.name") + "\n"
					+ "Started in: " + System.getProperty("user.dir") + "\n"
					+ "Java version: " + System.getProperty("java.version") + "\n"
					+ "Time: " + c.get(Calendar.HOUR) + ":"+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "\n"
					+ "Date: " + c.get(Calendar.YEAR) + "/"+ c.get(Calendar.MONTH) + "/" + c.get(Calendar.DATE) + "\n\n"
					+ "--------------------------------------------------------------------\n"
					+ "Pascal information:\n\n"
					+ "Pascal version: " + Game.version + "\n"
					+ "Pascal location: " + System.getProperty("user.dir") + "\n"
					+ "Nickname: " + Game.username + "\n\n"
					+ "--------------------------------------------------------------------\n"
					+ "Actual report:\n"
					+ "---------------\n\n"
					+ errors + "\n\n"
					+ "You can send this file to:\n"
					+ "xfoondom@gmail.com\n"
					+ "or, you can choose not to say anything about it. Please send it though. That would be very nice.";

			String updatedText = filet.replaceAll("\n", System.lineSeparator());
			out.print(updatedText);
			out.close();
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
