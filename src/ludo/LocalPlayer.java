package ludo;

public class LocalPlayer {
	private static GameState instance = null;
	
	private LocalPlayer() {
	}
	
	public static GameState getInstance() {
		if(instance == null) {
			instance = new GameState();
		}
		
		return instance;
	}
}
