package ludo;

import java.util.HashMap;
import java.util.HashSet;
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
		eligiblePlayers = GameRoom.getConnectedPlayers().keySet();
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
		System.out.println(buildMessage());
		if(!hasEveryoneFineshedRolling()) {
			return;
		}
		
		Set<Player> winningPlayers = getWinningPlayers();
		if(winningPlayers.size() == 1) {
			Player startingPlayer = winningPlayers.iterator().next();
			GameEngine.startGame(startingPlayer);
		} else {
		startNewTurn(winningPlayers);
		}
	}

	private static void startNewTurn(Set<Player> winningPlayers) {
		playersRoll.clear();
		eligiblePlayers = winningPlayers;
		if(eligiblePlayers.contains(LocalPlayer.getColor())) {			
			startTurn();
		}
	}

	private static Set<Player> getWinningPlayers() {
		Integer maxRoll = 0;
		Set<Player> winningPlayers = new HashSet<Player>();
		for(Map.Entry<Player, Integer> entry : playersRoll.entrySet()) {
			if(entry.getValue() == maxRoll) {
				winningPlayers.add(entry.getKey());
			}
			if(entry.getValue() > maxRoll) {
				maxRoll = entry.getValue();
				winningPlayers.clear();
				winningPlayers.add(entry.getKey());
			}
		}
		return winningPlayers;
	}

	private static boolean hasEveryoneFineshedRolling() {
		return playersRoll.keySet().equals(eligiblePlayers);
	}
	
	private static String buildMessage() {
		String msg = "First player election:\n";
		for(Player player : eligiblePlayers) {
			String nickname = GameRoom.getConnectedPlayers().get(player).getNickname();
			String playerColor = player.toString();
			Integer rollValue = playersRoll.get(player);
			String rollTxt = rollValue == null ? "-" : rollValue.toString();
			msg = msg + nickname + " (" + playerColor + ")" + ": " + rollTxt +"\n";
		}
		return msg;
	}
	
	
	
}
