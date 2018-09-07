package ludo;

import java.rmi.RemoteException;
import java.util.Map;

public class Ludo implements LudoInterface{	

	public Ludo() {
	}

	@Override
	public void connect(String name, LudoInterface gameIstance) throws RemoteException {
		PlayersMap registries = PlayersMap.getInstance();
		registries.put(name, gameIstance);
		GUI.showConnectedUsers();
		updateAll();
	}
	
	
	public static void updateAll() {
		boolean allPlayersReady = LocalPlayer.getInstance().checkIfAllPlayersAreReady();
		if(allPlayersReady) {
			FirstPlayerElection.begin();
		}
		
		PlayersMap registries = PlayersMap.getInstance();
		
		for(Map.Entry<String, LudoInterface> entry : registries.entrySet()) {
			if(entry.getKey().equals(LocalPlayer.getInstance().getNickname())) {
				continue;
			}
			
			LudoInterface remoteRegistry = entry.getValue();
			update(remoteRegistry);
		}
		if(allPlayersReady) {
			FirstPlayerElection.startTurn();
		}
	}
	
	public static void update(LudoInterface remoteRegistry) {
		try {
			remoteRegistry.update(PlayersMap.getInstance());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(PlayersMap registries) throws RemoteException {
		PlayersMap.updateAll(registries);
		boolean allPlayersReady = LocalPlayer.getInstance().checkIfAllPlayersAreReady();
		if(allPlayersReady) {
			FirstPlayerElection.begin();
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
	public void comunicateStartingRoll(String player, int rollValue) throws RemoteException {
		FirstPlayerElection.addRollForPlayer(player, rollValue);
	}
	

}
