package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public class Dispatcher implements Runnable {
	
	private static final int NUMBERS_OF_TASKS = 10;
	
	private final BlockingQueue<Call> buffer;
	
	public Dispatcher(BlockingQueue<Call> buffer) {
		this.buffer = buffer;
	}
	
	
	public void run() {
		createTasks();
	}
	
	private void createTasks() {
		for (int i = 0; i < NUMBERS_OF_TASKS; i++) {
			DispatcherTask task = new DispatcherTask(i);
			new Thread(task).start();
		}
	}
	
	
	
	private void dispatchCall(int taskId) {
		try {
			System.out.println("Task " + taskId + " - Revisando cola de calls");
			Call call = buffer.take();
			answer(taskId, call);
		} catch (InterruptedException e) {
			System.out.println("Task " + taskId + " - No hay llamadas pendientes");
		}
	}
	
	
	private void answer(int taskId, Call call) {
		try {
			System.out.println("Task " + taskId + " - Call " + call.getId() + " - Atendiendo");
			Thread.sleep(call.getTime() * 1000);
			System.out.println("Task " + taskId + " - " + call.getId() + " - Finalizada");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private class DispatcherTask implements Runnable {
		
		private int id;
		
		public DispatcherTask(int id) {
			super();
			this.id = id;
		}

		public void run() {
			while (true) {
				dispatchCall(this.id);
			}
		}
		
	}
}
