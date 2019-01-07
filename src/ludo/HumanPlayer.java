package ludo;

import java.io.Serializable;
import java.util.Objects;

public class HumanPlayer implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickname;
	private boolean isReady;
	private boolean isConnected = true;
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
	public void toggleReady() {
		isReady = !isReady;
	}
	public boolean isConnected() {
		return this.isConnected;
	}
	public void setConnected(boolean connected) {
		this.isConnected = connected;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null || (getClass() != other.getClass())) {			
			return false;
		}
		HumanPlayer otherPlayer = (HumanPlayer)other;
		return this.nickname.equals(otherPlayer.getNickname());		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nickname);
	}
	
//	public boolean checkIfAllPlayersAreReady() {
//		if(!isReady) return false;
//		
//		for(HumanPlayer player : GameRoom.getOthers().values()) {
//			System.out.println("checkIfAllPlayersAreReady" + player.nickname);	
//			try {
//				if(!player.getConnection().isReady()) {
//					return false;
//				}
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		}
//		return true;
//	}
	public LudoInterface getConnection() {
		return connection;
	}
	public void setConnection(LudoInterface connection) {
		this.connection = connection;
	}
}
