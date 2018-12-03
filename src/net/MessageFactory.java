package net;

import ludo.LocalPlayer;

public class MessageFactory {
	static int nextId = 0;
	
	public static Message create(String content) {
		Message msg = new Message(LocalPlayer.getInstance(), nextId, content);
		nextId++;
		return msg;
	}
}
