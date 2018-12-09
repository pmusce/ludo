package net;

import ludo.LocalPlayer;

public class MessageFactory {
	static int nextId = 0;
	
	public synchronized static Message create(Object content) {
		Message msg = new Message(LocalPlayer.getInstance(), nextId, content);
		nextId++;
		return msg;
	}
}
