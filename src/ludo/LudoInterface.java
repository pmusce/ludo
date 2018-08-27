package ludo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LudoInterface extends Remote{
	void connect(String nickname, LudoInterface gameIstance) throws RemoteException;
	boolean isReady() throws RemoteException;
	void update(PlayersMap registries) throws RemoteException;
}
