package ludo;

public enum Player {
	RED (0),
	GREEN (1),
	YELLOW (2),
	BLUE (3);
	
	private int index;
	Player(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public int getStartingSquare() {
		return index * 13;
	}
	
	
}
