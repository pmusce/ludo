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
		}
	}

	public static void play() {
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
		Player player = LocalPlayer.getColor();
		board.putIn(player);
		gBoard.update();
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
		System.out.println("Turn ended");
		
	}

	public static void moveToken(int position) {
		board.move(LocalPlayer.getColor(), position, rollValue);
		gBoard.update();
		if(rollValue == 6) {
			play();
		} else {
			GUI.showPass();
		}
	}
	
	public static void moveInsideHomeColumn(int position) {
		board.moveInsideHomeColumn(LocalPlayer.getColor(), position, rollValue);
		gBoard.update();
		play();
	}
	
	public static Player getActivePlayer() {
		return activePlayer;
	}

}
