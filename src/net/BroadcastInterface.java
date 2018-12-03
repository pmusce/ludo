package net;

public interface BroadcastInterface {

	void broadcast(Message message);

	void receive(Message message);

	void setMessageHandler(MessageHandler messageHandler);

}