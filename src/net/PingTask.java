package net;

import java.rmi.RemoteException;
import java.util.TimerTask;

import ludo.GameEngine;
import ludo.GameRoom;
import ludo.HumanPlayer;

public class PingTask extends TimerTask {

	@Override
	public void run() {
		HumanPlayer activePlayer = GameRoom.getInstance().get(GameEngine.getActivePlayer());
		try {
			activePlayer.getConnection().ping();
		} catch (RemoteException e) {
			GameRoom.disconnectPlayer(activePlayer);
			GameEngine.giveTurnToPlayer(GameRoom.getNext(GameEngine.getActivePlayer()));
		}
	}

}
