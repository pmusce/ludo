package ludo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EnumMap;

public interface LudoInterface extends Remote{
	void connect(HumanPlayer localPlayer) throws RemoteException;
	boolean isReady() throws RemoteException;
	void update(EnumMap<Player, HumanPlayer> registries) throws RemoteException;
	void comunicateStartingRoll(Player player, int rollValue) throws RemoteException;
	void giveTurn() throws RemoteException;
}
