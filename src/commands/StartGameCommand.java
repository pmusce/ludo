package commands;

import ludo.GameEngine;

public class StartGameCommand extends Command {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() {
		GameEngine.prepareGame();
	}

}
