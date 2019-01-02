package commands;

import gui.GUI;
import ludo.GameEngine;
import ludo.LocalPlayer;
import ludo.Player;

public class RollDiceCommand extends Command {
	private static final long serialVersionUID = 1L;

	private Player player;
	private int rollValue;

	public RollDiceCommand(Player player, int rollValue) {
		this.player = player;
		this.rollValue = rollValue;
	}
	@Override
	public void execute() {
		if(LocalPlayer.getColor().equals(player)) {
			GameEngine.handleRoll(rollValue);
		} else {
			GUI.showText(player.toString() + " player rolled " + rollValue);
		}
	}

}
