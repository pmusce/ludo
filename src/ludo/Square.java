package ludo;

import java.awt.Color;

public abstract class Square {
	public boolean isTokenOfPlayer(Player p) {
		return false;
	}
	
	public boolean isEmpty() {
		return true;
	}

	public Color getColor() {
		return Color.WHITE;
	}
}
