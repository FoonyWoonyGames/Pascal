package Util;

import Pascal.Game;

public class PString {
	public static void Convert(String str) {
		str.replace("[name]", Game.username);
	}
}
