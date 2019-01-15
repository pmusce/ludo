package ludo;

import java.rmi.RemoteException;
import java.util.EnumMap;

import net.BroadcastReceiver;
import net.Message;

public class Ludo implements LudoInterface {

	public Ludo() {
	}

	@Override
	public void connect(HumanPlayer player) throws RemoteException {
		GameRoom.addPlayer(player);
		updateAll();
	}

	public static void updateAll() {
		EnumMap<Player, HumanPlayer> registries = GameRoom.getInstance();
		HumanPlayer localPlayer = LocalPlayer.getInstance();
		for (HumanPlayer player : registries.values()) {
			if (player.equals(localPlayer)) {
				continue;
			}

			LudoInterface remoteRegistry = player.getConnection();
			update(remoteRegistry);
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
		GameRoom.printPlayers();
	}

	@Override
	public void sendMessage(Message message) throws RemoteException {
		BroadcastReceiver.enqueueMessage(message);
	}

	@Override
	public void ping() throws RemoteException {
		// If this returns without throwing an exception, 
		// it means that the connection is still alive
	}

	
}
