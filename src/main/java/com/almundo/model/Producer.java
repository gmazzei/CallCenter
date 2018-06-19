package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
	
	private final BlockingQueue<Call> buffer;
	private Integer callCounter;
	
	public Producer(BlockingQueue<Call> buffer) {
		this.buffer = buffer;
		this.callCounter = 0;
	}

	public void run() {
		while (true) {
			Call call = generateCall();
			addCall(call);
			waitSomeTime();
		}
	}
	
	
	private void addCall(Call call) {
		System.out.println("Productor - Call " + call.getId() + " - Agregando - Tiempo: " + call.getTime());
		boolean isAccepted = buffer.offer(call);
		
		if (isAccepted) {
			System.out.println("Productor - Call " + call.getId() + " - Agregada");
		} else {
			System.out.println("Productor - Call " + call.getId() + " - Rechazada");
		}
	}
	
	
	private Call generateCall() {
		Integer id = callCounter++;
		Integer time = Double.valueOf((Math.random() * 5000 + 5000)).intValue();
		return new Call(id, time);
	}
	
	private void waitSomeTime() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
