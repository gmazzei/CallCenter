package com.almundo.model;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class Employee {
	
	private static Logger log = Logger.getLogger(Employee.class);
	private Integer id;
	private Position position;
	private BlockingQueue<Employee> queue;
	
	public Employee(Integer id, Position position, BlockingQueue<Employee> queue) {
		this.id = id;
		this.position = position;
		this.queue = queue;
	}
	
	public void answer(Call call) {
		try {
			log.debug(position.getName() + " " + id + " is answering Call " + call.getId());
			Thread.sleep(call.getTime());
			log.debug(position.getName() + " " + id + " has finished Call " + call.getId() + " - Time spent: " + call.getTime() + " milliseconds");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public BlockingQueue<Employee> getOwnQueue() {
		return queue;
	}
}
