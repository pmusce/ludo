package ludo;

import java.rmi.RemoteException;

import gui.GUI;
import gui.GUIBoard;

public class GameEngine {
	private static Board board ;
	private static GUIBoard gBoard;
	private static Player activePlayer;
	private static Integer rollValue;
	private static Integer turn;

	public static void prepareGame() {
		turn = 0;
		board = new Board();
		gBoard = GUI.createBoardFrame(board);
		FirstPlayerElection.begin();
	}
	
	public static void startGame(Player startingPlayer) {
		activePlayer = startingPlayer;
		gBoard.update();
		if(isMyTurn()) {
			play();
		} else {
			System.out.println("Wait your turn");
			GUI.showWaiting();
		}
	}

	public static void play() {
		activePlayer = LocalPlayer.getColor();
		GUI.showRoll();
		System.out.println("Turn " + turn);
		System.out.println(board.toString());
	}

	private static boolean isMyTurn() {
		return activePlayer.equals(LocalPlayer.getColor());
	}

	public static void rollDice() {
		rollValue = Dice.roll();
		
		
		System.out.println("Roll: " + rollValue);
		GUI.showText("Roll: " + rollValue);
		Player player = LocalPlayer.getColor();
		Ludo.communicateRoll(rollValue);
		boolean canMove = board.hasPlayerAvailableMoves(player, rollValue);
		boolean canPutToken = rollValue == 6 && board.hasPlayerUnusedToken(player);
		
		if(canMove || canPutToken) {
			GUI.showMoveAndPutToken(canMove, canPutToken);
		}
		else {
			GUI.showPass();
		}
	}

	public static void playToken() {
		Ludo.communicatePlayToken();
		Player player = LocalPlayer.getColor();
		playerPlaysToken(player);
		if(rollValue == 6) {
			play();
		}
	}

	public static void passTurn() {
		// TODO Auto-generated method stub
		turn++;
		GUI.showWaiting();
		Player nextPlayer = GameRoom.getNext(activePlayer);
		try {
			GameRoom.getInstance().get(nextPlayer).getConnection().giveTurn();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activePlayer = nextPlayer;
		System.out.println("Turn ended");
		gBoard.update();
		
	}

	public static void moveToken(int position) {
		Player player = LocalPlayer.getColor();
		moveTokenForPlayer(player, position, rollValue);
		Ludo.communicateMoveToken(position, rollValue);
		if(rollValue == 6) {
			play();
		} else {
			GUI.showPass();
		}
	}

	public static void moveTokenForPlayer(Player player, int position, Integer steps) {
		board.move(player, position, steps);
		gBoard.update();
	}
	
	
	public static void moveInsideHomeColumn(int position) {
		board.moveInsideHomeColumn(LocalPlayer.getColor(), position, rollValue);
		gBoard.update();
		play();
	}
	
	public static Player getActivePlayer() {
		return activePlayer;
	}

	public static void playerPlaysToken(Player player) {
		board.putIn(player);
		gBoard.update();
	}

}
