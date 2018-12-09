package commands;

import ludo.FirstPlayerElection;
import ludo.LocalPlayer;
import ludo.Player;
import net.BroadcastFactory;
import net.Message;
import net.MessageFactory;

public class CommandDeliverer {
	private static void broadcastCommand(Command command) {
		Message msg = MessageFactory.create(command);
		Runnable broadcaster = new Runnable() {
            public void run() {
            	BroadcastFactory.getInstance().broadcast(msg);
            }
          };
		new Thread(broadcaster).start();
		
	}
	
	public static void toggleReady() {
    	Command command = new ReadyCommand(LocalPlayer.getInstance());
		broadcastCommand(command);
	}
	
	public static void comunicateStartingRoll(int currentRoll) {
		Player currentPlayer = LocalPlayer.getColor();
		Command command = new StartingRollCommand(currentPlayer, currentRoll);
		broadcastCommand(command);
	}
	
	public static void startGame() {
		Command command = new StartGameCommand();
		broadcastCommand(command);
	}
	
	
}
