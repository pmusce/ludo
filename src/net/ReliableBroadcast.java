package net;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ludo.GameRoom;
import ludo.HumanPlayer;

public class ReliableBroadcast extends Broadcast {	
	private Set<Message> delivered;
	private MessageHandler messageHandler;
	
	public ReliableBroadcast() {
		delivered = new HashSet<Message>();
	}
	
	public void broadcast(Message message) {
		delivered.add(message);
		messageHandler.deliver(message);
		sendAll(GameRoom.getOtherConnectedPlayers().values(), message);
		
	}

	public synchronized void receive(Message message) {
		if(!delivered.contains(message)) {
			delivered.add(message);
			messageHandler.deliver(message);
			Collection<HumanPlayer> receivers = GameRoom.getOtherConnectedPlayers().values();
			receivers.remove(message.getSender());
			sendAll(receivers, message);			
		}	
	}


	@Override
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
}
