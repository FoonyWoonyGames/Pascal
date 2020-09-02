package Util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class PColor {
	
	public static Color BLACK = new Color(0, 0, 0, 255);
	public static Color WHITE = new Color(255, 255, 255, 255);
	public static Color GRAY = new Color(125, 125, 125, 255);
	public static Color GREY = new Color(125, 125, 125, 255);
	public static Color LIGHT_GRAY = new Color(190, 190, 190, 255);
	public static Color LIGHT_GREY = new Color(190, 190, 190, 255);
	public static Color DARK_GRAY = new Color(50, 50, 50, 255);
	public static Color DARK_GREY = new Color(50, 50, 50, 255);
	public static Color DARK_BLUE = new Color(0, 63, 171, 255);
	public static Color BLUE = new Color(0, 140, 255, 255);
	public static Color LIGHT_BLUE = new Color(133, 200, 225, 255);
	public static Color DARK_GREEN = new Color(0, 115, 0, 255);
	public static Color GREEN = new Color(0, 207, 0, 255);;
	public static Color LIGHT_GREEN = new Color(0, 255, 0, 255);
	public static Color DARK_YELLOW = new Color(150, 150, 0, 255);
	public static Color YELLOW = new Color(245, 228, 0, 255);
	public static Color LIGHT_YELLOW = new Color(255, 246, 120, 255);
	public static Color DARK_ORANGE = new Color(171, 100, 0, 255);
	public static Color ORANGE = new Color(255, 149, 0, 255);
	public static Color LIGHT_ORANGE = new Color(255, 197, 115, 255);
	public static Color DARK_RED = new Color(180, 0, 0, 255);
	public static Color RED = new Color(255, 0, 0, 255);
	public static Color LIGHT_RED = new Color(255, 125, 125, 255);
	public static Color MAGENTA2 = new Color(255, 0, 150, 255);
	public static Color DARK_PURPLE = new Color(115, 0, 113, 255);
	public static Color MAGENTA = new Color(255, 0, 255, 255);
	public static Color PURPLE = new Color(217, 0, 213, 255);
	public static Color LIGHT_PURPLE = new Color(255, 117, 253, 255);
	public static Color PINK = new Color(255, 117, 253, 255);
	public static Color BORDEAUX = new Color(130, 20, 54, 255);
	public static Color AQUA = new Color(0, 255, 255, 255);
	public static Color LIME = new Color(0, 255, 0, 255);
	public static Color BROWN = new Color(117, 87, 43, 255);
	
	// MISC
	public static Color INTERFACE = new Color(230, 230, 0, 255);
	public static Color ERROR = new Color(255, 0, 65, 255);
	public static Color TIP = new Color(40, 190, 240, 255);
	public static Color OBJECTIVE = new Color(160, 16, 160, 255);
	
	public static Color ITEM_USELESS = new Color(240, 240, 240);
	public static Color ITEM_NORMAL = new Color(230, 255, 75);
	public static Color ITEM_GOOD = new Color(75, 210, 255);
	public static Color ITEM_LEGENDARY = new Color(205, 5, 60);
	
	// Change color of an image
	public static void colorize(BufferedImage img, Color col) {
		try {
			for (int i = 0; i < img.getWidth(); i++) {
				for (int j = 0; j < img.getHeight(); j++) {
					int rgb = img.getRGB(i, j);
					
					int a = (rgb>>24)&0xff;
					int r = (rgb>>16)&col.getRed();
					int g = (rgb>>8)&col.getGreen();
					int b = (rgb>>0)&col.getBlue();
					
					rgb = (a<<24) + (r << 16) + (g << 8) + b;
					
					img.setRGB(i, j,rgb);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static BufferedImage getColorized(BufferedImage img, Color col) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(img, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 255));
        g.fillRect(0,0,w,h);
        g.dispose();
        return dyed;
	}
	public static BufferedImage copy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
}
