package net;

import commands.Command;
import commands.CommandReceiver;

public class LudoMessageHandler implements MessageHandler{
	@Override
	public void deliver(Message message) {
		Command command = (Command)message.getContent();
		CommandReceiver.enqueueCommand(command);
	}
}
