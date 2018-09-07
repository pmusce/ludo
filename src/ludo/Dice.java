package ludo;

import java.util.Random;

public class Dice {
	private static Random rnd;
	public static Integer roll() {
		if(rnd == null) rnd = new Random();
		return rnd.nextInt(6) + 1;
	}
	
}
