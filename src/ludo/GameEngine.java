package ludo;

import gui.GUI;
import gui.GUIBoard;

public class GameEngine {
	private static Board board ;
	private static GUIBoard gBoard;
	private static String activePlayer;
	private static Integer rollValue;

	public static void startGame(String startingPlayer) {
		activePlayer = startingPlayer;
		board = new Board();
		gBoard = GUI.createBoardFrame(board);
		if(isMyTurn()) {
			play();
		} else {
			System.out.println("Wait your turn");
		}
	}

	private static void play() {
		// enable rolling dice
		System.out.println("PLAY!");
		System.out.println(board.toString());
	}

	private static boolean isMyTurn() {
		return activePlayer.equals(LocalPlayer.getInstance().getNickname());
	}

	public static void rollDice() {
		rollValue = Dice.roll();
				
		System.out.println("Roll: " + rollValue);
		Player player = Player.RED;
		
		if(rollValue == 6 && board.hasPlayerUnusedToken(player)) {
			if(board.hasPlayerAvailableMoves(player, rollValue)) {
				chooseWhatToDo();
			} else {
				board.putIn(player);
				gBoard.update();
			}
			play();
		} else {
			if(board.hasPlayerAvailableMoves(player, rollValue)) {
				move();
			}
			System.out.println(board.toString());
			passTurn();
		}
		
	}

	private static void move() {
		System.out.println("Choose what to move");
	}

	private static void chooseWhatToDo() {
		System.out.println("Roll or move?");	
	}

	private static void passTurn() {
		// TODO Auto-generated method stub
		System.out.println("Turn ended");
	}

	public static void moveToken(int position) {
		board.move(Player.RED, position, rollValue);
		gBoard.update();
		play();
	}
	
	public static void moveInsideHomeColumn(int position) {
		board.moveInsideHomeColumn(Player.RED, position, rollValue);
		gBoard.update();
		play();
	}

}
