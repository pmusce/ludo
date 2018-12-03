package net;

public class SimpleMessageHandler implements MessageHandler {
	@Override
	public void deliver(Message message) {
		String output = "Received message from " + message.getSender().getNickname();
		output += " with ID " + message.getId();
		System.out.println(output);
	}
}
