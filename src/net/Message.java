package net;

import java.io.Serializable;
import java.util.Objects;

import ludo.HumanPlayer;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private HumanPlayer sender;
	private int id;
	private Object content;
	
	public Message(HumanPlayer sender, int id, Object content) {
		this.sender = sender;
		this.id = id;
		this.content = content;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null || (getClass() != other.getClass())) {			
			return false;
		}
		Message otherMessage = (Message)other;
		return sender.equals(otherMessage.getSender()) && id == otherMessage.getId();
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(sender, id);
	}

	public HumanPlayer getSender() {
		return sender;
	}

	public void setSender(HumanPlayer sender) {
		this.sender = sender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	public boolean hasSameSender(Message msg) {
		return this.sender.equals(msg.getSender());
	}
	
	@Override
	public String toString() {
		return "["+sender.getNickname()+"] #" + id ;	
	}
}
