package ludo;

import java.awt.Color;

public enum Player {
	RED (0, Color.RED),
	GREEN (1, Color.GREEN),
	YELLOW (2, Color.YELLOW),
	BLUE (3, Color.BLUE);
	
	private int index;
	private Color color;
	Player(int index, Color color) {
		this.index = index;
		this.color = color;
	}

	public int getIndex() {
		return index;
	}

	public int getStartingSquare() {
		return index * 13;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Player next() {
		switch (this) {
		case RED:
			return GREEN;
		case GREEN:
			return YELLOW;
		case YELLOW:
			return BLUE;
		case BLUE:
			return RED;
		}
		return null;
	}
}
