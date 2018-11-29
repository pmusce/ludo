package ludo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import gui.GUI;

public class FirstPlayerElection {
	private static Map <Player, Integer> playersRoll;
	private static boolean eligible;
	private static Set<Player> eligiblePlayers;
	private static Semaphore lock = new Semaphore(0, true);
	
	public synchronized static void begin() {
		System.out.println("Starting election for first player");
		eligible = true;
		eligiblePlayers = GameRoom.getInstance().keySet();
		if(playersRoll == null) {			
			playersRoll = new HashMap<Player, Integer>();
			lock.release();
		}
	}

	public synchronized static void startTurn() {
		if(!eligible) {
			throw new UnsupportedOperationException();
		}
		
		GUI.showText(buildMessage());
		int currentRoll = Dice.roll();
		System.out.println(currentRoll);
		comunicateStartingRoll(currentRoll);
		Player currentPlayer = LocalPlayer.getColor();
		addRollForPlayer(currentPlayer, currentRoll);
	}
	
	private synchronized static void comunicateStartingRoll(int currentRoll) {
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

	public synchronized static void addRollForPlayer(Player p, int rollValue) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playersRoll.put(p,rollValue);
		lock.release();
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

	private synchronized static boolean hasEveryoneFineshedRolling() {
		return playersRoll.keySet().equals(eligiblePlayers);
	}
	
	private synchronized static String buildMessage() {
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
