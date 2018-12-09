package net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ludo.GameRoom;
import ludo.HumanPlayer;
import ludo.LocalPlayer;

public class CausalBroadcast extends FifoBroadcast implements MessageHandler {
	private Set<CausalMessage> buffer;
	private HashMap<HumanPlayer, Integer> VC;
	
	private MessageHandler messageHandler;
	
	public CausalBroadcast() {
		super();
		buffer = new HashSet<CausalMessage>();
		VC = new HashMap<HumanPlayer, Integer>();
		for (HumanPlayer p : GameRoom.getInstance().values()) {
			VC.put(p, 0);
		}
	}

	public void broadcast(Message message) {
		HashMap<HumanPlayer, Integer> TS;
		synchronized (this) {			
			VC.put(LocalPlayer.getInstance(), VC.get(LocalPlayer.getInstance()) + 1);
			TS = new HashMap<HumanPlayer, Integer>(VC);
		}
		CausalMessage msg = new CausalMessage(message, TS);
		
		super.broadcast(new Message(message.getSender(), message.getId(), msg));
	}

	@Override
	public void setMessageHandler(MessageHandler messageHandler) {
		super.setMessageHandler(this);
		this.messageHandler = messageHandler;
	}
	
	@Override
	public synchronized void deliver(Message message) {
		CausalMessage cMsg = (CausalMessage)message.getContent();
		buffer.add(cMsg);
		while(isAnyMessageReadyForDelivery()) {
			CausalMessage nextMsg = getNextMessage();
			messageHandler.deliver(nextMsg.getContent());
			updateVC(nextMsg.getTS());
			buffer.remove(nextMsg);
		}
	}
	
	private void updateVC(Map<HumanPlayer, Integer> ts) {
		System.out.println("VC");
		printVC(VC);
		System.out.println("ts");
		printVC(ts);
		for(Entry<HumanPlayer, Integer> e : ts.entrySet()) {
			if(VC.get(e.getKey()) < e.getValue()) {
				VC.put(e.getKey(), e.getValue());
			}
		}
		System.out.println("new VC");
		printVC(VC);
	}

	private boolean isAnyMessageReadyForDelivery() {
		return getNextMessage() != null;
	}
	
	private CausalMessage getNextMessage() {
		for (CausalMessage m : buffer) {
			HumanPlayer sender = m.getContent().getSender();
			
			if(!isNextMessageOfSender(m, sender)) {
				continue;
			}
			
			if(!isCausalConsistent(m, sender)) {
				continue;
			}
			return m;
		}
		return null;
	}

	private boolean isCausalConsistent(CausalMessage msg, HumanPlayer sender) {
		for(HumanPlayer s : GameRoom.getInstance().values()) {
			if(s.equals(sender)) 
				continue;
			if(VC.get(s) < msg.getTS().get(s))
				return false;
		}
		return true;
	}
	
	private boolean isNextMessageOfSender(CausalMessage msg, HumanPlayer sender) {
		return isNextMessageOfCurrentPlayer(msg, sender) || VC.get(sender) == msg.getTS().get(sender) - 1;
	}

	private boolean isNextMessageOfCurrentPlayer(CausalMessage msg, HumanPlayer sender) {
		return sender.equals(LocalPlayer.getInstance()) && (VC.get(sender) == msg.getTS().get(sender));	
	}
	
	private void printVC(Map<HumanPlayer, Integer> map) {
		String s = "";
		for(Integer n:map.values()) {
			s+=n + ",";
		}
		System.out.println(s);
		
	}

}
