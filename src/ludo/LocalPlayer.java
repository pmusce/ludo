package ludo;

import java.util.Map.Entry;

public class LocalPlayer {
	private static HumanPlayer instance = null;
	
	private LocalPlayer() {
	}
	
	public static HumanPlayer getInstance() {
		if(instance == null) {
			instance = new HumanPlayer();
		}
		return instance;
	}
	
	public static Player getColor() {
		for(Entry<Player, HumanPlayer> p : GameRoom.getInstance().entrySet()) {
			if(p.getValue().equals(instance)) {
				return p.getKey();
			}
		}
		return null;
	}
}
