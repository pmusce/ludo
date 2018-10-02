package ludo;

import java.rmi.RemoteException;
import java.util.EnumMap;

import gui.GUI;

public class Ludo implements LudoInterface{	

	public Ludo() {
	}

	@Override
	public void connect(HumanPlayer player) throws RemoteException {
		GameRoom.addPlayer(player);
		GUI.showConnectedUsers();
		updateAll();
	}
	

	public static void updateAll() {
		boolean allPlayersReady = LocalPlayer.getInstance().checkIfAllPlayersAreReady();
		if(allPlayersReady) {
			GameEngine.prepareGame();
		}
		
		EnumMap<Player, HumanPlayer> registries = GameRoom.getInstance();
		HumanPlayer localPlayer = LocalPlayer.getInstance();
		for(HumanPlayer player : registries.values()) {
			if(player.equals(localPlayer)) {
				continue;
			}
			
			LudoInterface remoteRegistry = player.getConnection();
			update(remoteRegistry);
		}
		if(allPlayersReady) {
			FirstPlayerElection.startTurn();
		}
	}

	public static void update(LudoInterface remoteRegistry) {
		try {
			remoteRegistry.update(GameRoom.getInstance());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(EnumMap<Player, HumanPlayer> registries) throws RemoteException {
		GameRoom.updateAll(registries);
		boolean allPlayersReady = LocalPlayer.getInstance().checkIfAllPlayersAreReady();
		if(allPlayersReady) {
			GameEngine.prepareGame();
			FirstPlayerElection.startTurn();
		}
		GUI.showConnectedUsers();
	}

	@Override
	public boolean isReady() throws RemoteException {
		HumanPlayer gameState = LocalPlayer.getInstance();
		return gameState.isReady();
	}

	@Override
	public void comunicateStartingRoll(Player player, int rollValue) throws RemoteException {
		FirstPlayerElection.addRollForPlayer(player, rollValue);
	}

	@Override
	public void giveTurn() throws RemoteException {
		GameEngine.play();
		
	}

	
	
	

}
