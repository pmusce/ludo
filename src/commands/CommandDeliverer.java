package commands;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ludo.LocalPlayer;
import ludo.Player;
import net.BroadcastFactory;
import net.Message;
import net.MessageFactory;

public class CommandDeliverer implements Runnable {
	private static BlockingQueue<Message> queue;
	
	public CommandDeliverer() {
		queue = new LinkedBlockingQueue<Message>();
	}
	
	private static void broadcastCommand(Command command) {
		Message msg = MessageFactory.create(command);
		try {
			queue.put(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void toggleReady() {
    	Command command = new ReadyCommand(LocalPlayer.getInstance());
		broadcastCommand(command);
	}
	
	
	public static void startGame() {
		Command command = new StartGameCommand();
		broadcastCommand(command);
	}
	
	public static void comunicateStartingRoll(int currentRoll) {
		Player currentPlayer = LocalPlayer.getColor();
		Command command = new StartingRollCommand(currentPlayer, currentRoll);
		broadcastCommand(command);
	}

	public static void communicateRollDice(int rollValue) {
		Player currentPlayer = LocalPlayer.getColor();
		Command command = new RollDiceCommand(currentPlayer, rollValue);
		broadcastCommand(command);
	}

	@Override
	public void run() {
		try {
			while (true) {
				Message msg = queue.take();
				BroadcastFactory.getInstance().broadcast(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
