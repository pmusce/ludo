package ludo;

import java.rmi.RemoteException;
import java.util.EnumMap;

import net.BroadcastFactory;
import net.Message;
	
public class Ludo implements LudoInterface{	

	public Ludo() {
	}

	@Override
	public void connect(HumanPlayer player) throws RemoteException {
		GameRoom.addPlayer(player);
		//GUI.showConnectedUsers();
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
//		boolean allPlayersReady = LocalPlayer.getInstance().checkIfAllPlayersAreReady();
		boolean allPlayersReady = false;
		if(allPlayersReady) {
			GameEngine.prepareGame();
			FirstPlayerElection.startTurn();
		}
		//GUI.showConnectedUsers();
	}

//	@Override
//	public boolean isReady() throws RemoteException {
//		HumanPlayer gameState = LocalPlayer.getInstance();
//		return gameState.isReady();
//	}
//
//	@Override
//	public void comunicateStartingRoll(Player player, int rollValue) throws RemoteException {
//		FirstPlayerElection.addRollForPlayer(player, rollValue);
//	}
//
//	@Override
//	public void giveTurn() throws RemoteException {
//		GameEngine.play();
//		
//	}

	public static void communicateRoll(Integer rollValue) {
		for(HumanPlayer player : GameRoom.getOthers().values()) {
//			try {
//				player.getConnection().sendRoll(LocalPlayer.getColor(), rollValue);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
	}

//	@Override
//	public void sendRoll(Player color, Integer rollValue) throws RemoteException {
//		// TODO Auto-generated method stub
//		GUI.showText(color.toString() + " player rolled " + rollValue);
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
		BroadcastFactory.getInstance().receive(message);
	}

	
	
	

}
