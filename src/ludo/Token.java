package ludo;

public class Token extends Square {
	private Player player;
	
	public Token(Player player) {
		this.player = player;
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
}
