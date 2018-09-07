package ludo;

import java.util.HashMap;
import java.util.Set;

public class PlayersMap extends HashMap<String,LudoInterface>{
	private static final long serialVersionUID = 1L;
	private static PlayersMap instance = null;

	
	private PlayersMap() {
	}
	
	public static PlayersMap getInstance() {
		if(instance == null) {
			instance = new PlayersMap();
		}
		
		return instance;
	}

	public static void updateAll(PlayersMap players) {
		instance = players;
	}
	
	public static HashMap<String,LudoInterface> getOthers() {
		PlayersMap otherPlayers;
		otherPlayers = (PlayersMap) instance.clone();
		otherPlayers.remove(LocalPlayer.getInstance().getNickname());
		return otherPlayers;
	}
	
	public static Set<String> getPlayers() {
		return instance.keySet();
	}

}
