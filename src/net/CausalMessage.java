package net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ludo.HumanPlayer;

public class CausalMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Message content;
	private HashMap<HumanPlayer, Integer> TS;
	
	
	public CausalMessage(Message message, HashMap<HumanPlayer, Integer> vC) {
		content = message;
		TS = vC;
	}
	
	
	public Message getContent() {
		return content;
	}
	
	public Map<HumanPlayer, Integer> getTS() {
		return TS;
	}
}