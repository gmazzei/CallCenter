package com.almundo.model;

import org.apache.log4j.Logger;

public class Employee {
	
	private static Logger log = Logger.getLogger(Employee.class);
	private Integer id;
	private Position position;
	
	public Employee(Integer id, Position position) {
		this.id = id;
		this.position = position;
	}
	
	public void answer(Call call) {
		try {
			log.debug(getName() + " is answering Call " + call.getId());
			Thread.sleep(call.getTime());
			log.debug(getName() + " has finished Call " + call.getId() + " - Time spent: " + call.getTime() + " milliseconds");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Position getPosition() {
		return position;
	}
	
	public String getName() {
		return position.getName() + " " + id;
	}
	
}
