package ludo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EnumMap;

import net.Message;

public interface LudoInterface extends Remote{
	void connect(HumanPlayer localPlayer) throws RemoteException;
	boolean isReady() throws RemoteException;
	void update(EnumMap<Player, HumanPlayer> registries) throws RemoteException;
	void comunicateStartingRoll(Player player, int rollValue) throws RemoteException;
	void giveTurn() throws RemoteException;
	void sendRoll(Player color, Integer rollValue) throws RemoteException;
	void sendPlayToken(Player color) throws RemoteException;
	void sendMoveToken(Player color, int position, int steps) throws RemoteException;
	void sendMessage(Message message) throws RemoteException;
}
