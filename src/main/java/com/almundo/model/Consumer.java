package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
	
	private final BlockingQueue<Call> buffer;
	
	public Consumer(BlockingQueue<Call> buffer) {
		this.buffer = buffer;
	}
	
	public void run() {
		while (true) {
			try {
				System.out.println("Consumidor - Revisando cola de calls");
				Call call = buffer.take();
				answer(call);
			} catch (InterruptedException e) {
				System.out.println("Consumidor - No hay llamadas pendientes");
			}
		}
	}
	
	private void answer(Call call) {
		try {
			System.out.println("Consumidor - Call " + call.getId() + " - Atendiendo");
			Thread.sleep(call.getTime() * 1000);
			System.out.println("Consumidor - Call " + call.getId() + " - Finalizada");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
