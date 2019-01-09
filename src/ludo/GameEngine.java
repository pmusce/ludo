package ludo;

import java.util.Timer;

import commands.CommandDeliverer;
import gui.GUI;
import gui.GUIBoard;
import net.PingTask;

public class GameEngine{
	private static boolean gameStarted = false;
	private static Board board ;
	private static GUIBoard gBoard;
	private static Player activePlayer;
	private static Integer rollValue;
	private static Integer turn;

	public synchronized static void prepareGame() {
		if(gameStarted) {
			return;
		}
		gameStarted = true;
		turn = 0;
		board = new Board();
		gBoard = GUI.createBoardFrame(board);
		FirstPlayerElection.begin();
	}
	
	public static void startGame(Player startingPlayer) {
		giveTurnToPlayer(startingPlayer);
		if(isMyTurn()) {
			play();
		} else {
			System.out.println("Wait your turn");
			GUI.showWaiting();
		}
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new PingTask(), 3000, 3000);
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
		CommandDeliverer.communicateRollDice(rollValue);
	}

	public static void handleRoll(int rollValue) {
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
		CommandDeliverer.playToken();
		
		if(rollValue == 6) {
			play();
		}
	}

	public static void passTurn() {
		turn++;
		GUI.showWaiting();
		Player nextPlayer = GameRoom.getNext(activePlayer);
		CommandDeliverer.passTurn(nextPlayer);
		System.out.println("Turn ended");
	}

	public static void giveTurnToPlayer(Player nextPlayer) {
		activePlayer = nextPlayer;
		gBoard.update();
		if(LocalPlayer.getColor().equals(activePlayer)) {
			GameEngine.play();
		}
	}
	
	public static void moveToken(int position) {
		CommandDeliverer.communicateMoveToken(position, rollValue);
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

	public static void endGame(Player winner) {
		System.out.println("-------");
		System.out.println("Game over");
		if(LocalPlayer.getColor().equals(winner)) {
			System.out.println("You win!");
		} else {
			System.out.println("You loose.");
			System.out.println(winner.toString() + " wins!");
		}
		System.out.println("-------");
		System.exit(0);
		
	}

}
