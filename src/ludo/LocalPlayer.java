package ludo;

public class LocalPlayer {
	private static HumanPlayer instance = null;
	
	private LocalPlayer() {
	}
	
	public static HumanPlayer getInstance() {
		if(instance == null) {
			instance = new HumanPlayer();
		}
		
		return instance;
	}
}
