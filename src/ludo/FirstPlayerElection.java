package ludo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gui.GUI;

public class FirstPlayerElection {
	private static Map <Player, Integer> playersRoll;
	private static boolean eligible;
	private static Set<Player> eligiblePlayers;
	private static int turn;
	
	public static void begin() {
		System.out.println("Starting election for first player");
		eligible = true;
		turn = 0;
		eligiblePlayers = GameRoom.getInstance().keySet();
		playersRoll = new HashMap<Player, Integer>();
	}

	public static void startTurn() {
		if(!eligible) {
			throw new UnsupportedOperationException();
		}
		
		int currentRoll = Dice.roll();
		System.out.println(currentRoll);
		GUI.showText("Election Roll:" + currentRoll);
		comunicateStartingRoll(currentRoll);
		Player currentPlayer = LocalPlayer.getColor();
		addRollForPlayer(currentPlayer, currentRoll);
	}
	
	private static void comunicateStartingRoll(int currentRoll) {
		Player currentPlayer = LocalPlayer.getColor();
		
		for(HumanPlayer player : GameRoom.getOthers().values()) {
			try {
				player.getConnection().comunicateStartingRoll(currentPlayer, currentRoll);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void addRollForPlayer(Player p, int rollValue) {
		playersRoll.put(p,rollValue);
		if(hasEveryoneFineshedRolling()) {
			Integer maxRoll = 0;
			Player winningPlayer = null;
			for(Map.Entry<Player, Integer> entry : playersRoll.entrySet()) {
				if(entry.getValue() > maxRoll) {
					maxRoll = entry.getValue();
					winningPlayer = entry.getKey();
				}
			}
			GameEngine.startGame(winningPlayer);
		}
	}

	private static boolean hasEveryoneFineshedRolling() {
		return playersRoll.keySet().equals(eligiblePlayers);
	}
	
	
	
}
