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
		PlayersMap registries = PlayersMap.getInstance();
		
		for(Map.Entry<String, LudoInterface> entry : registries.entrySet()) {
			if(entry.getKey().equals(LocalPlayer.getInstance().getNickname())) {
				continue;
			}
			
			LudoInterface remoteRegistry = entry.getValue();
			update(remoteRegistry);
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
		GUI.showConnectedUsers();
	}

	@Override
	public boolean isReady() throws RemoteException {
		GameState gameState = LocalPlayer.getInstance();
		return gameState.isReady();
	}

}
