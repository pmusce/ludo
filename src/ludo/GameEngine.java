package ludo;

public class GameEngine {

	private static Board board ;
	private static String activePlayer;

	public static void startGame(String startingPlayer) {
		activePlayer = startingPlayer;
		board = new Board();
		if(isMyTurn()) {
			System.out.println("PLAY!");
		} else {
			System.out.println("Wait your turn");
		}
	}

	private static boolean isMyTurn() {
		return activePlayer.equals(LocalPlayer.getInstance().getNickname());
	}

}
