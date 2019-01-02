package commands;

import ludo.GameEngine;
import ludo.Player;

public class PassTurnCommand extends Command {
	private static final long serialVersionUID = 1L;
	private Player nextPlayer;

	public PassTurnCommand(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	@Override
	public void execute() {
		GameEngine.giveTurnToPlayer(nextPlayer);
	}

}
