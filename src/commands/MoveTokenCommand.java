package commands;

import ludo.GameEngine;
import ludo.Player;

public class MoveTokenCommand extends Command {
	private static final long serialVersionUID = 1L;
	private Player player;
	private int position;
	private Integer rollValue;

	public MoveTokenCommand(Player player, int position, Integer rollValue) {
		this.player = player;
		this.position = position;
		this.rollValue = rollValue;
	}

	@Override
	public void execute() {
		GameEngine.moveTokenForPlayer(player, position, rollValue);
	}

}
