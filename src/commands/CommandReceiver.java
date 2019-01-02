package commands;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandReceiver implements Runnable {
	private static BlockingQueue<Command> queue;
	
	public CommandReceiver() {
		queue = new LinkedBlockingQueue<Command>();
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

	private void consume(Command cmd) {
		cmd.execute();
	}

	public static void enqueueCommand(Command cmd) {
		try {
			queue.put(cmd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
