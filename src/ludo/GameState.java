package ludo;

import java.rmi.RemoteException;
import java.util.Map;

public class GameState {
	private String nickname;
	private boolean isReady;
	
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
	private boolean checkIfAllPlayersAreReady() {
		for(Map.Entry<String, LudoInterface> player : PlayersMap.getOthers().entrySet()) {
			System.out.println(player.getKey());
			try {
				if(!player.getValue().isReady()) {
					return false;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}
