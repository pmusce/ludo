package ludo;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map.Entry;

import gui.GUI;

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
	
	public static EnumMap<Player, HumanPlayer> getConnectedPlayers() {
		EnumMap<Player, HumanPlayer> otherPlayers = new EnumMap<Player, HumanPlayer>(instance.clone());
		for(Entry<Player, HumanPlayer> p : otherPlayers.entrySet()) {
			if(!p.getValue().isConnected()) {
				otherPlayers.remove(p.getKey());
			}
		}
		return otherPlayers;
	}
	
	public static EnumMap<Player, HumanPlayer> getOtherConnectedPlayers() {
		EnumMap<Player, HumanPlayer> otherPlayers = getConnectedPlayers();
		otherPlayers.remove(LocalPlayer.getColor());
		return otherPlayers;
	}
	

	public static void addPlayer(HumanPlayer player) {
		Player color = selectColor();
		instance.put(color, player);
		printPlayers();
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
		while(!instance.containsKey(result) || !instance.get(result).isConnected()) {
			result = result.next();
		}
		return result;
	}
	
	public static void togglePlayerReady(HumanPlayer player) {
		for(HumanPlayer p : getInstance().values()) {
			if(p.equals(player)) {
				p.toggleReady();
			}
		}
		printPlayers();
	}
	
	public static boolean areAllPlayersReady() {
		for(HumanPlayer player : getInstance().values()) {
			if(!player.isReady()) {
				return false;
			}
		}
		return true;
	}
	
	public static void printPlayers() {
		for(HumanPlayer player : getInstance().values()) {
			String str = player.getNickname() + " - ";
			str += player.isReady() ? "ready" : "wait";
			System.out.println(str);
		}
		System.out.println();
	}
	
	public static void disconnectPlayer(HumanPlayer player) {
		System.out.println(player.getNickname() + " has disconnected");
		for(HumanPlayer p : getInstance().values()) {
			if(p.equals(player)) {
				p.setConnected(false);
			}
		}
		
		if(getOtherConnectedPlayers().isEmpty()) {
			GameEngine.endGame(LocalPlayer.getColor());
		}
		
		GUI.updateBoard();
	}

}
