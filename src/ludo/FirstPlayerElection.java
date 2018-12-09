package ludo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import commands.CommandDeliverer;
import gui.GUI;

public class FirstPlayerElection {
	private static Map <Player, Integer> playersRoll;
	private static boolean eligible;
	private static Set<Player> eligiblePlayers;
	
	public static void begin() {
		System.out.println("Starting election for first player");
		eligible = true;
		eligiblePlayers = GameRoom.getInstance().keySet();
		if(playersRoll == null) {			
			playersRoll = new HashMap<Player, Integer>();
		}
		startTurn();
	}

	public static void startTurn() {
		if(!eligible) {
			throw new UnsupportedOperationException();
		}
		
		GUI.showText(buildMessage());
		int currentRoll = Dice.roll();
		System.out.println(currentRoll);
		CommandDeliverer.comunicateStartingRoll(currentRoll);
	}

	public static void addRollForPlayer(Player p, int rollValue) {
		playersRoll.put(p,rollValue);
		GUI.showText(buildMessage());
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
	
	private static String buildMessage() {
		String msg = "First player election:\n";
		for(Player player : eligiblePlayers) {
			String nickname = GameRoom.getInstance().get(player).getNickname();
			String playerColor = player.toString();
			Integer rollValue = playersRoll.get(player);
			String rollTxt = rollValue == null ? "-" : rollValue.toString();
			msg = msg + nickname + " (" + playerColor + ")" + ": " + rollTxt +"\n";
		}
		return msg;
	}
	
	
	
}
