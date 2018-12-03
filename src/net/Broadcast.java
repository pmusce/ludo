package net;

import java.rmi.RemoteException;
import java.util.Collection;

import ludo.HumanPlayer;

public abstract class Broadcast implements BroadcastInterface {

	protected static void sendAll(Collection<HumanPlayer> receivers, Message message) {
		for(HumanPlayer p : receivers) {
			try {
				p.getConnection().sendMessage(message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
