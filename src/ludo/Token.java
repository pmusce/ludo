package ludo;

import java.awt.Color;

public class Token extends Square {
	private Player player;
	
	public Token(Player player) {
		this.player = player;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public String toString() {
		String result = "";
		switch (player) {
		case RED:
			result = "r";
			break;
		case GREEN:
			result = "g";
			break;
		case YELLOW:
			result = "y";
			break;
		case BLUE:
			result = "b";
			break;
		default:
			break;
		}
		return result;
	}
	
	public boolean isTokenOfPlayer(Player p) {
		return p == player;
	}
	
	public Color getColor() {
		switch (player) {
		case RED:
			return Color.RED;
		case GREEN:
			return Color.GREEN;
		case YELLOW:
			return Color.YELLOW;
		case BLUE:
			return Color.BLUE;
		}
		return null;
	}
}
