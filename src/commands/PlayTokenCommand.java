package commands;

import ludo.GameEngine;
import ludo.Player;

public class PlayTokenCommand extends Command {
	private static final long serialVersionUID = 1L;
	private Player player;

	public PlayTokenCommand(Player player) {
		this.player = player;
	}

	@Override
	public void execute() {
		GameEngine.playerPlaysToken(player);
	}

}
