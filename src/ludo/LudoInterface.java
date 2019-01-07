package ludo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EnumMap;

import net.Message;

public interface LudoInterface extends Remote{
	void connect(HumanPlayer localPlayer) throws RemoteException;
	void update(EnumMap<Player, HumanPlayer> registries) throws RemoteException;
	void sendMessage(Message message) throws RemoteException;
	void ping() throws RemoteException;
}
