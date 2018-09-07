package ludo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FirstPlayerElection {
	private static Map <String, Integer> playersRoll;
	private static boolean eligible;
	private static Set<String> eligiblePlayers;
	private static int turn;
	
	public static void begin() {
		System.out.println("Starting election for first player");
		eligible = true;
		turn = 0;
		eligiblePlayers = PlayersMap.getPlayers();
		playersRoll = new HashMap<String, Integer>();
	}

	public static void startTurn() {
		if(!eligible) {
			throw new UnsupportedOperationException();
		}
		
		int currentRoll = Dice.roll();
		System.out.println(currentRoll);
		comunicateStartingRoll(currentRoll);
		String currentPlayer = LocalPlayer.getInstance().getNickname();
		addRollForPlayer(currentPlayer, currentRoll);
	}
	
	private static void comunicateStartingRoll(int currentRoll) {
		String currentPlayer = LocalPlayer.getInstance().getNickname();
		
		for(LudoInterface player : PlayersMap.getOthers().values()) {
			try {
				player.comunicateStartingRoll(currentPlayer, currentRoll);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void addRollForPlayer(String p, int rollValue) {
		playersRoll.put(p,rollValue);
		if(hasEveryoneFineshedRolling()) {
			Integer maxRoll = 0;
			String winningPlayer = null;
			for(Map.Entry<String, Integer> entry : playersRoll.entrySet()) {
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
