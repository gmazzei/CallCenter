package com.almundo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Dispatcher implements Runnable {
	
	private static final int NUMBERS_OF_TASKS = 10;
	private static final int NUMBERS_OF_EMPLOYEES = 5;
	
	private final BlockingQueue<Call> buffer;
	private final BlockingQueue<Employee> employees;
	
	public Dispatcher(BlockingQueue<Call> buffer) {
		this.buffer = buffer;
		this.employees = new ArrayBlockingQueue<Employee>(NUMBERS_OF_EMPLOYEES, true, createEmployees());
	}
	
	
	public void run() {
		createTasks();
	}
	
	private List<Employee> createEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		for (int i = 0; i < NUMBERS_OF_EMPLOYEES; i++) {
			employees.add(new Employee(i));
		}
		return employees;
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
			Employee employee = employees.take();
			Call call = buffer.take();
			employee.answer(call, taskId);
			employees.put(employee);
			
		} catch (InterruptedException e) {
			System.out.println("Task " + taskId + " - No hay llamadas pendientes");
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
