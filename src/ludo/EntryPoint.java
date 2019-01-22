package ludo;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import commands.CommandDeliverer;
import commands.CommandReceiver;
import gui.GUI;
import net.BroadcastReceiver;

public class EntryPoint {
	private static Registry localRegistry;
	private static LudoInterface gameIstance; 	// this is needed to keep a strong reference to the object exposed
										// otherwise collected by the gc
	
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Usage:");
			System.out.println("ludo nickname [port] [remote_port] [remote_ip]");
			System.exit(0);
		}
		
		String nickname = args[0];
		int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1099; 
		
		boolean isCreatingRoom = (args.length <= 2);
		
		HumanPlayer localPlayer = LocalPlayer.getInstance();
		localPlayer.setNickname(nickname);
		
		BroadcastReceiver broadcastReceiver = new BroadcastReceiver();
		new Thread(broadcastReceiver).start();
		
		CommandDeliverer commandDeliverer = new CommandDeliverer();
		new Thread(commandDeliverer).start();
		
		CommandReceiver commandConsumer = new CommandReceiver();
		new Thread(commandConsumer).start();
		
		createRegistry(port);
		GUI.start();
		
		if(!isCreatingRoom) {
			int remotePort = Integer.parseInt(args[2]);
			String remoteHost = (args.length > 3) ? args[3] : null; 
			
			connectToRemotePlayer(nickname, remoteHost, remotePort);
		}
		
		
	}
	
	private static void connectToRemotePlayer(String nickname, String remoteHost, int remotePort) {
		try {
			Registry remoteRegistry = LocateRegistry.getRegistry(remoteHost, remotePort);
			LudoInterface remoteClient = (LudoInterface) remoteRegistry.lookup("ludo");
			HumanPlayer localPlayer = LocalPlayer.getInstance();
			localPlayer.setConnection(gameIstance);
			remoteClient.connect(localPlayer);
		} catch(RemoteException | NotBoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void createRegistry(int port) {
		gameIstance = new Ludo();
		try {
			localRegistry = LocateRegistry.createRegistry(port);
			HumanPlayer p = LocalPlayer.getInstance();
			p.setConnection(gameIstance);
			GameRoom.addPlayer(p);
			
			LudoInterface stub = (LudoInterface) UnicastRemoteObject.exportObject(gameIstance, 0);
			localRegistry.rebind("ludo", stub);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	

}
