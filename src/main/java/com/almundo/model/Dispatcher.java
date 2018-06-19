package com.almundo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class Dispatcher implements Runnable {
	
	private static final int NUMBERS_OF_TASKS = 10;
	private static final int NUMBERS_OF_OPERATORS = 3;
	private static final int NUMBERS_OF_SUPERVISORS = 2;
	private static final int NUMBERS_OF_DIRECTORS = 1;
	
	private final BlockingQueue<Call> callQueue;
	private final BlockingQueue<Operator> operators;
	private final BlockingQueue<Supervisor> supervisors;
	private final BlockingQueue<Director> directors;
	
	public Dispatcher(BlockingQueue<Call> callQueue) {
		this.callQueue = callQueue;
		this.operators = new ArrayBlockingQueue<Operator>(NUMBERS_OF_OPERATORS, true, createOperators());
		this.supervisors = new ArrayBlockingQueue<Supervisor>(NUMBERS_OF_SUPERVISORS, true, createSupervisors());
		this.directors = new ArrayBlockingQueue<Director>(NUMBERS_OF_DIRECTORS, true, createDirectors());
	}
	
	
	public void run() {
		createTasks();
	}
	
	private List<Operator> createOperators() {
		List<Operator> operators = new ArrayList<Operator>();
		for (int i = 0; i < NUMBERS_OF_OPERATORS; i++) {
			operators.add(new Operator(i));
		}
		return operators;
	}
	
	private List<Supervisor> createSupervisors() {
		List<Supervisor> supervisors = new ArrayList<Supervisor>();
		for (int i = 0; i < NUMBERS_OF_SUPERVISORS; i++) {
			supervisors.add(new Supervisor(i));
		}
		return supervisors;
	}
	
	private List<Director> createDirectors() {
		List<Director> directors = new ArrayList<Director>();
		for (int i = 0; i < NUMBERS_OF_DIRECTORS; i++) {
			directors.add(new Director(i));
		}
		return directors;
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
		if (employee instanceof Operator) {
			this.operators.offer((Operator) employee);
		} else if (employee instanceof Supervisor) {
			this.supervisors.offer((Supervisor) employee);
		} else {
			this.directors.offer((Director) employee);
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
				dispatchCall();
			}
		}
		
	}
}
