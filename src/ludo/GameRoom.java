package ludo;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class GameRoom {
	private static EnumMap<Player, HumanPlayer> instance = null;
	
	private GameRoom() {
	}
	
	public static EnumMap<Player, HumanPlayer> getInstance() {
		if(instance == null) {
			instance = new EnumMap<Player, HumanPlayer>(Player.class);
		}
		
		return instance;
	}

	public static void updateAll(EnumMap<Player, HumanPlayer> players) {
		instance = players;
	}
	
	public static EnumMap<Player, HumanPlayer> getOthers() {
		EnumMap<Player, HumanPlayer> otherPlayers = new EnumMap<Player, HumanPlayer>(instance.clone());
		otherPlayers.remove(LocalPlayer.getColor());
		return otherPlayers;
	}
	
	public static Set<String> getPlayers() {
		Set<String> result = new HashSet<String>();
		for(HumanPlayer p : instance.values()) {
			result.add(p.getNickname());
		}
		return result;
	}

	public static void addPlayer(HumanPlayer player) {
		Player color = selectColor();
		instance.put(color, player);
	}

	private static Player selectColor() {
		if(instance == null) {
			instance = new EnumMap<Player, HumanPlayer>(Player.class);
		}

		EnumSet<Player> availableColors = EnumSet.allOf(Player.class);
		availableColors.removeAll(instance.keySet());
		return availableColors.iterator().next();
	}
	
	public static Player getNext(Player current) {
		Player result = current.next();
		while(!instance.containsKey(result)) {
			result = result.next();
		}
		return result;
	}

	public static Set<LudoInterface> getConnections() {
		Set<LudoInterface> connections = new HashSet<LudoInterface>();
		for(HumanPlayer p : getInstance().values()) {
			connections.add(p.getConnection());
		}
		return connections;
	}
	
	public static Set<LudoInterface> getOthersConnections() {
		Set<LudoInterface> connections = new HashSet<LudoInterface>();
		for(HumanPlayer p : getOthers().values()) {
			connections.add(p.getConnection());
		}
		return connections;
	}

}
