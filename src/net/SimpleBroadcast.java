package net;

import ludo.GameRoom;

// Not used in current implementation
public class SimpleBroadcast extends Broadcast {
	private static SimpleBroadcast instance = null;

	public SimpleBroadcast() {
	}

	public static SimpleBroadcast getInstance() {
		if (instance == null)
			instance = new SimpleBroadcast();

		return instance;
	}

	public void broadcast(Message message) {
		sendAll(GameRoom.getConnectedPlayers().values(), message);
	}

	public void receive(Message message) {
		deliver(message);
	}

	private void deliver(Message message) {
		System.out.println("Received message: " + message.getContent());
	}

	@Override
	public void setMessageHandler(MessageHandler messageHandler) {
		
	}

	
}
