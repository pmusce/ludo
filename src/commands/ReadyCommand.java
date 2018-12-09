package commands;

import ludo.FirstPlayerElection;
import ludo.GameEngine;
import ludo.GameRoom;
import ludo.HumanPlayer;

public class ReadyCommand extends Command {
	private static final long serialVersionUID = 1L;
	private HumanPlayer player;

	public ReadyCommand(HumanPlayer player) {
		this.player = player;
	}

	@Override
	public void execute() {
		GameRoom.togglePlayerReady(player);
		if(GameRoom.areAllPlayersReady()) {
			CommandDeliverer.startGame();
		}
		
	}

}
