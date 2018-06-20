package com.almundo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Dispatcher implements Runnable {
	
	private static final int NUMBERS_OF_TASKS = 10;
	private static final int NUMBERS_OF_OPERATORS = 6;
	private static final int NUMBERS_OF_SUPERVISORS = 3;
	private static final int NUMBERS_OF_DIRECTORS = 1;
	
	private final BlockingQueue<Call> callQueue;
	private final BlockingQueue<Employee> operators;
	private final BlockingQueue<Employee> supervisors;
	private final BlockingQueue<Employee> directors;
	
	public Dispatcher(BlockingQueue<Call> callQueue) {
		this.callQueue = callQueue;
		this.operators = createOperatorsQueue();
		this.supervisors = createSupervisorsQueue();
		this.directors = createDirectorsQueue();
	}
	
	
	private BlockingQueue<Employee> createOperatorsQueue() {
		
		BlockingQueue<Employee> queue = new ArrayBlockingQueue<Employee>(NUMBERS_OF_OPERATORS, true);
		Position position = new Operator();
		
		List<Employee> operators = new ArrayList<Employee>();
		for (Integer i = 0; i < NUMBERS_OF_OPERATORS; i++) {
			operators.add(new Employee(i, position, queue));
		}
		
		queue.addAll(operators);
		
		return queue;
	}
	
	private BlockingQueue<Employee> createSupervisorsQueue() {
			
		BlockingQueue<Employee> queue = new ArrayBlockingQueue<Employee>(NUMBERS_OF_SUPERVISORS, true);
		Position position = new Supervisor();
		
		List<Employee> supervisors = new ArrayList<Employee>();
		for (Integer i = 0; i < NUMBERS_OF_SUPERVISORS; i++) {
			supervisors.add(new Employee(i, position, queue));
		}
		
		queue.addAll(supervisors);
		
		return queue;
	}
	
	private BlockingQueue<Employee> createDirectorsQueue() {
		
		BlockingQueue<Employee> queue = new ArrayBlockingQueue<Employee>(NUMBERS_OF_DIRECTORS, true);
		Position position = new Director();
		
		List<Employee> directors = new ArrayList<Employee>();
		for (Integer i = 0; i < NUMBERS_OF_DIRECTORS; i++) {
			directors.add(new Employee(i, position, queue));
		}
		
		queue.addAll(directors);
		
		return queue;
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
	
	
	
	private void dispatchCall() {
		boolean isEmployeeAvailable = false;
		boolean isCallWaiting = false;
		Employee employee = null;
		Call call = null;
		
		synchronized (this) {
			isEmployeeAvailable = isEmployeeAvailable();
			isCallWaiting = isCallWaiting();
			
			if (isEmployeeAvailable && isCallWaiting) {
				 employee = getAvailableEmployee();
				 call = getWaitingCall();
			}
		}
		
		if (isEmployeeAvailable && isCallWaiting) {
			employee.answer(call);
			putEmployee(employee);
		}
		
	}
	
	
	private boolean isEmployeeAvailable() {
		return !operators.isEmpty() || !supervisors.isEmpty() || !directors.isEmpty();
	}
	
	private boolean isCallWaiting() {
		return !callQueue.isEmpty();
	}
	
	private Employee getAvailableEmployee() {
		Employee employee = operators.poll();
		
		if (employee == null) {
			employee = supervisors.poll();
		} 
		
		if (employee == null) {
			employee = directors.poll();
		}
		
		return employee;
	}
	
	private Call getWaitingCall() {
		return callQueue.poll();
	}
	
	private void putEmployee(Employee employee) {
		BlockingQueue<Employee> queue = employee.getOwnQueue();
		queue.offer(employee);
	}
	
	private class DispatcherTask implements Runnable {
		
		private int id;
		
		public DispatcherTask(int id) {
			super();
			this.id = id;
		}

		public void run() {
			while (true) {
				dispatchCall();
			}
		}
		
	}
}
