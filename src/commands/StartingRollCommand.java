package commands;

import ludo.FirstPlayerElection;
import ludo.Player;

public class StartingRollCommand extends Command {
	private static final long serialVersionUID = 1L;
	private Player player;
	private int rollValue;

	public StartingRollCommand(Player player, int rollValue) {
		this.player = player;
		this.rollValue = rollValue;
	}

	@Override
	public void execute() {
		FirstPlayerElection.addRollForPlayer(player, rollValue);
	}

}
