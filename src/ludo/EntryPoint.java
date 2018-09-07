package ludo;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EntryPoint {
	private static Registry localRegistry;
	private static LudoInterface gameIstance; 	// this is needed to keep a strong reference to the object exposed
										// otherwise collected by the gc
	
	public static void main(String[] args) {
		int port = (args.length > 0) ? Integer.parseInt(args[0]) : 1099; 
		String nickname = Integer.toString(port);
		boolean isCreatingRoom = (args.length <= 1);
		
		HumanPlayer localPlayer = LocalPlayer.getInstance();
		localPlayer.setNickname(nickname);
		
		createRegistry(port);
		if(!isCreatingRoom) {
			int remotePort = Integer.parseInt(args[1]);
			connectToRemotePlayer(nickname, remotePort);
		}
		
		GUI.start();
	}
	
	private static void connectToRemotePlayer(String nickname, int remotePort) {
		try {
			Registry remoteRegistry = LocateRegistry.getRegistry(remotePort);
			LudoInterface remoteClient = (LudoInterface) remoteRegistry.lookup("ludo");
			remoteClient.connect(nickname, gameIstance);
		} catch(RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	private static void createRegistry(int port) {
		gameIstance = new Ludo();
		PlayersMap registryList = PlayersMap.getInstance();
		try {
			localRegistry = LocateRegistry.createRegistry(port);
			registryList.put(Integer.toString(port), gameIstance);
			
			LudoInterface stub = (LudoInterface) UnicastRemoteObject.exportObject(gameIstance, 0);
			localRegistry.rebind("ludo", stub);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	

}
