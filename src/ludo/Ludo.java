package ludo;

import java.rmi.RemoteException;
import java.util.EnumMap;

import net.BroadcastFactory;
import net.BroadcastReceiver;
import net.Message;
	
public class Ludo implements LudoInterface{	

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
		for(HumanPlayer player : registries.values()) {
			if(player.equals(localPlayer)) {
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
	}

//	@Override
//	public void giveTurn() throws RemoteException {
//		GameEngine.play();
//		
//	}

	public static void communicatePlayToken() {
		for(HumanPlayer player : GameRoom.getOthers().values()) {
//			try {
//				player.getConnection().sendPlayToken(LocalPlayer.getColor());
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

//	@Override
//	public void sendPlayToken(Player player) throws RemoteException {
//		GameEngine.playerPlaysToken(player);
//		
//	}

	public static void communicateMoveToken(int position, int steps) {
		for(HumanPlayer player : GameRoom.getOthers().values()) {
//			try {
//				player.getConnection().sendMoveToken(LocalPlayer.getColor(), position, steps);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
//
//	@Override
//	public void sendMoveToken(Player player, int position, int steps) throws RemoteException {
//		GameEngine.moveTokenForPlayer(player, position, steps);
//	}

	@Override
	public void sendMessage(Message message) throws RemoteException {
		BroadcastReceiver.enqueueMessage(message);
	}

	
	
	

}
