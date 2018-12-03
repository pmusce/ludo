package net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ludo.GameRoom;
import ludo.HumanPlayer;

public class FifoBroadcast extends ReliableBroadcast implements MessageHandler {
	private Set<Message> buffer;
	private Map<HumanPlayer, Integer> next;
	private MessageHandler messageHandler;

	public FifoBroadcast() {
		super();
		buffer = new HashSet<Message>();
		next = new HashMap<HumanPlayer, Integer>();
		for (HumanPlayer p : GameRoom.getInstance().values()) {
			next.put(p, 0);
		}
	}

	public void broadcast(Message message) {
		super.broadcast(message);
	}

	@Override
	public void setMessageHandler(MessageHandler messageHandler) {
		super.setMessageHandler(this);
		this.messageHandler = messageHandler;
	}

	@Override
	public void deliver(Message message) {
		System.out.println("R-deliver " + message.toString());

		buffer.add(message);
		while (isAnyMessageReadyForDelivery(message)) {
			Message nextMsg = getNextMessage(message);
			System.out.println("F-deliver " + nextMsg.toString());

			messageHandler.deliver(nextMsg);
			int nextValue = next.get(nextMsg.getSender());
			next.put(nextMsg.getSender(), nextValue + 1);
			buffer.remove(nextMsg);
		}

	}

	private Message getNextMessage(Message message) {
		for (Message m : buffer) {
			if (m.hasSameSender(message)) {
				HumanPlayer sender = message.getSender();
				if (next.get(sender) == m.getId()) {
					return m;
				}
			}
		}
		return null;
	}

	private boolean isAnyMessageReadyForDelivery(Message message) {
		return getNextMessage(message) != null;
	}
}
