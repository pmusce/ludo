package net;

import commands.Command;

public class LudoMessageHandler implements MessageHandler{
	@Override
	public void deliver(Message message) {
		String output = "Received from " + message.getSender().getNickname();
		output += " with ID " + message.getId() + ": " + message.getContent().getClass().toString();
		System.out.println(output);
		
		Command action = (Command)message.getContent();
		
       	action.execute();     	
	}
}
