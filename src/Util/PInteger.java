package Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Random;

public class PInteger {
	public static double round(double val, int dec) {
	    if (dec < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(val);
	    bd = bd.setScale(dec, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public static int convertTimeProcent() {
	    Calendar calendar = Calendar.getInstance();
		long seconds = calendar.get(Calendar.SECOND);
		long minutes = calendar.get(Calendar.MINUTE);
		long hours = calendar.get(Calendar.HOUR_OF_DAY);
		long time = ((hours*60)*60) + (minutes*60) + seconds;
		
		return (int) (time/864);
	}
	public static int randomBetween(int a, int b) {
		Random r = new Random();
		int num1 = a;
		int num2 = b;
		return r.nextInt(num1-num2) + num2;
	}
	public static double randomDouble() {
		Random r = new Random();
		return r.nextDouble();
	}
	public static int randomProcent() {
		Random r = new Random();
		return r.nextInt(100);
	}
}
