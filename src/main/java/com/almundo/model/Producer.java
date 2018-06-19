package com.almundo.model;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class Producer implements Runnable {
	
	private static Logger log = Logger.getLogger(Producer.class);
	private static final int TIME_BETWEEN_CALLS = 3000;
	
	private BlockingQueue<Call> callQueue;
	private Integer callIdCounter;
	
	public Producer(BlockingQueue<Call> callQueue) {
		this.callQueue = callQueue;
		this.callIdCounter = 0;
	}

	public void run() {
		while (true) {
			Call call = generateCall();
			addCall(call);
			waitFakeTime();
		}
	}
	
	
	private void addCall(Call call) {
		log.debug("Producer - Call " + call.getId() + " has arrived");
		boolean isAccepted = callQueue.offer(call);
		
		if (isAccepted) {
			log.debug("Producer - Call " + call.getId() + " has been added to the queue");
		} else {
			log.debug("Producer - Call " + call.getId() + " has been rejected - Queue is full");
		}
	}
	
	
	private Call generateCall() {
		Integer id = callIdCounter++;
		return new Call(id);
	}
	

	
	/**
	 * Fake time between calls.
	 */
	private void waitFakeTime() {
		try {
			Thread.sleep(TIME_BETWEEN_CALLS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
