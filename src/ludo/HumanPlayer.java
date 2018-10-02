package ludo;

import java.io.Serializable;
import java.rmi.RemoteException;

public class HumanPlayer implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickname;
	private boolean isReady;
	private LudoInterface connection;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isReady() {
		return isReady;
	}
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	public void toggleReady() {
		isReady = !isReady;
		if(isReady) {
			if(checkIfAllPlayersAreReady()) {
				System.out.println("ALL READY");
				// TODO Start game
			}
		}
	}
	
	public boolean equals(HumanPlayer obj) {
		return this.nickname.equals(obj.getNickname());		
	}
	
	public boolean checkIfAllPlayersAreReady() {
		if(!isReady) return false;
		
		for(HumanPlayer player : GameRoom.getOthers().values()) {
			System.out.println("checkIfAllPlayersAreReady" + player.nickname);	
			try {
				if(!player.getConnection().isReady()) {
					return false;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return true;
	}
	public LudoInterface getConnection() {
		return connection;
	}
	public void setConnection(LudoInterface connection) {
		this.connection = connection;
	}
}