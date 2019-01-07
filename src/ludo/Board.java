package ludo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

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
		Arrays.fill(regular, new EmptySquare());

		homes = new EnumMap<Player, Square[]>(Player.class);
		starting = new EnumMap<Player, Integer>(Player.class);
		finishing = new EnumMap<Player, Integer>(Player.class);

		for (Player p : Player.values()) {
			starting.put(p, 4);
			finishing.put(p, 0);
			homes.put(p, createEmptySquares(5));
		}
	}

	public String toString() {
		String s = "";
		s += printEnumMap(starting) + "\n";

		for (Square square : regular)
			s += square.toString();
		s += "\n";

		for (Square[] h : homes.values())
			for (Square square : h)
				s += square.toString();
		s += "\n";

		s += printEnumMap(finishing);

		return s;
	}

	private String printEnumMap(EnumMap<Player, Integer> map) {
		StringBuilder sb = new StringBuilder(256);

		for (Integer n : map.values()) {
			sb.append(n.toString());
			sb.append("-");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private Square[] createEmptySquares(Integer length) {
		Square[] result = new Square[length];
		Arrays.fill(result, new EmptySquare());
		return result;
	}
	
	private void putTokenInSquare(Square[] s, int position, Token t) {
		s[position] = t;
	}

	public void putIn(Player player) {
		Integer availableTokens = starting.get(player);
		starting.put(player, availableTokens - 1);
		putTokenInSquare(regular, player.getStartingSquare(), new Token(player));
	}


	public int move(Player player, int tokenPosition, int steps) {
		Token token = (Token) regular[tokenPosition];
		regular[tokenPosition] = new EmptySquare();

		int newPosition = (tokenPosition + steps) % regular.length;
		if (isGoingToHomeColumn(player, tokenPosition, steps)) {
			Square[] playerHome = homes.get(player);
			newPosition = newPosition - player.getStartingSquare();
			playerHome[newPosition] = token;
		} else {
			regular[newPosition] = token;
		}

		return newPosition;
	}

	public boolean isGoingToHomeColumn(Player player, int tokenPosition, int steps) {
		int normalizedPosition = (tokenPosition - player.getStartingSquare() + regular.length) % regular.length;
		return (normalizedPosition + steps >= regular.length);
	}

	public void moveInsideHomeColumn(Player player, int tokenPosition, int steps) throws MoveNotAllowedException {
		if (tokenPosition + steps >= 6) {
			throw new MoveNotAllowedException();
		}

		Square[] playerHome = homes.get(player);

		if (tokenPosition + steps == 5) {
			Integer finishLine = finishing.get(player);
			finishing.put(player, finishLine + 1);
		} else {
			Token token = (Token) playerHome[tokenPosition];
			playerHome[tokenPosition + steps] = token;
		}

		playerHome[tokenPosition] = new EmptySquare();
	}

	public boolean hasPlayerActiveTokens(Player p) {
		return starting.get(p) + finishing.get(p) < 4;
	}

	public boolean hasPlayerAvailableMoves(Player p, int steps) {
		for (Square s : regular) {
			if (s.isTokenOfPlayer(p))
				return true;
		}
		Square[] playerHome = homes.get(p);
		for (int i = 0; i < playerHome.length; i++) {
			if (playerHome[i].isTokenOfPlayer(p)) {
				if (i + steps < 6)
					return true;
			}
		}
		return false;
	}

	public boolean hasPlayerUnusedToken(Player p) {
		return starting.get(p) > 0;
	}

	public Square[] getRegular() {
		return regular;
	}

	public EnumMap<Player, Square[]> getHomes() {
		return homes;
	}

	public EnumMap<Player, Integer> getStarting() {
		return starting;
	}

	public EnumMap<Player, Integer> getFinishing() {
		return finishing;
	}

	public List<Integer> getMovableTokens(Player p, int steps) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < regular.length; i++) {
			Square s = regular[i];
			if (s.isTokenOfPlayer(p)) {
				result.add(i);
			}
		}
		Square[] playerHome = homes.get(p);
		for (int i = 0; i < playerHome.length; i++) {
			if (playerHome[i].isTokenOfPlayer(p)) {
				if (i + steps < 6)
					result.add(i + 13 * playersNumber);
			}
		}

		return result;
	}
}
