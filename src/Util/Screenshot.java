package Util;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;

import Pascal.Game;

public class Screenshot {
	public static void click(){
		try {
			Sound soundPlayer = new Sound();
			soundPlayer.play("gui.screenshot");
			soundPlayer.setVolume(-(80.0F-(80.0F/(100.0F/Game.settings.soundInterface))));
			
			File dir = new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/screenshots");
			dir.mkdir();

        	int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			String currentDate = "Y" + Calendar.getInstance().get(Calendar.YEAR)+"_M" + month +
					"_W"+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)+"_D"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+
					"_H"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+"_M"+Calendar.getInstance().get(Calendar.MINUTE)+
					"_S"+Calendar.getInstance().get(Calendar.SECOND)+"_MS"+Calendar.getInstance().get(Calendar.MILLISECOND);
			
			BufferedImage screen = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(screen, "png", new File(System.getProperty("user.home") + "/AppData/Roaming/Foony Woony Games/Pascal/screenshots/" + currentDate + ".png"));
		} catch (HeadlessException | AWTException | IOException e) {
			e.printStackTrace();
		}
	}
}
