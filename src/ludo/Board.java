package ludo;

import java.util.Arrays;
import java.util.EnumMap;

public class Board {
	private int playersNumber = Player.values().length;
	// Regular Squares 13 * 4
	private Square[] regular;
	// Home Squares 5 * 4
	private EnumMap<Player, Square[]> homes;
	// Starting Squares (Numbers of available tokens)
	private EnumMap<Player, Integer> starting;
	// Finishing Squares (Numbers of tokens that reached the finish square)
	private EnumMap<Player, Integer> finishing;
	
	public Board() {
		regular = new Square[13 * playersNumber];
		Arrays.fill(regular, new Square());
		
		homes = new EnumMap<Player, Square[]>(Player.class);		
		starting = new EnumMap<Player, Integer>(Player.class);
		finishing = new EnumMap<Player, Integer>(Player.class);
		
		for(Player p : Player.values()) {
			starting.put(p, 4);
			finishing.put(p, 0);
			homes.put(p, createEmptySquares(5));
		}
	}
	
	private Square[] createEmptySquares(Integer length) {
		Square[] result = new Square[length];
		Arrays.fill(result, new Square());
		return result;
	}
	
	public void putIn(Player player) {
		Integer availableTokens = starting.get(player);
		starting.put(player, availableTokens - 1);
		regular[player.getStartingSquare()] = new Token(player);
	}
	
	public String toString() {
		String s = "";
		s += printEnumMap(starting) + "\n";
		
		for(Square square : regular) 
			s += square.toString();
		s += "\n";
		
		for(Square[] h : homes.values())
			for(Square square : h) 
				s += square.toString();
		s += "\n";
		
		s += printEnumMap(finishing);
		
		return s;
	}
	
	private String printEnumMap(EnumMap<Player, Integer> map) {
		StringBuilder sb = new StringBuilder(256);
		
		for(Integer n : map.values()) {
			sb.append(n.toString());
			sb.append("-");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}


	private String printArrays(Object[] arr) {
		StringBuilder sb = new StringBuilder(256);
		sb.append(arr[0]);
		
		for(int i = 1; i < arr.length; i++) sb.append('-').append(arr[i]);
		
		return sb.toString();
	}
}
