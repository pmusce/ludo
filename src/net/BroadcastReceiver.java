package net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class BroadcastReceiver implements Runnable {
	private static BlockingQueue<Message> queue;
	
	public BroadcastReceiver() {
		queue = new LinkedBlockingQueue<Message>();
	}

	@Override
	public void run() {
		try {
			while (true) {
				consume(queue.take());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void consume(Message msg) {
		BroadcastFactory.getInstance().receive(msg);
	}

	public static void enqueueMessage(Message msg) {
		try {
			queue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
