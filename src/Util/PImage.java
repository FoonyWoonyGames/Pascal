package Util;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class PImage {
	public static Color getColorAt(BufferedImage img, int x, int y) {
		int pixel = img.getRGB(x, y);
		int  red   = (pixel & 0x00ff0000) >> 16;
		int  green = (pixel & 0x0000ff00) >> 8;
		int  blue  =  pixel & 0x000000ff;
		
		return new Color(red, green, blue);
	}
}
