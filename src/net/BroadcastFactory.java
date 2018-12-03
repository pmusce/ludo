package net;


public class BroadcastFactory {
	private static BroadcastInterface instance = null;
	
	public static BroadcastInterface getInstance() {
		if (instance == null) {
			instance = createBroadcast();
		}
		return instance;
	}
	
	private static BroadcastInterface createBroadcast() {
		BroadcastInterface broadcast = new CausalBroadcast();
		broadcast.setMessageHandler(new SimpleMessageHandler());
		return broadcast;
	}
}
