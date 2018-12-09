package net;

public class SimpleMessageHandler implements MessageHandler {
	@Override
	public void deliver(Message message) {
		String output = "Received from " + message.getSender().getNickname();
		output += " with ID " + message.getId() + ": ";
		output += (String)message.getContent();
		System.out.println(output);
	}
}
