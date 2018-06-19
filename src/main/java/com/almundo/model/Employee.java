package com.almundo.model;

import org.apache.log4j.Logger;

public abstract class Employee {
	
	private static Logger log = Logger.getLogger(Employee.class);
	private Integer id;
	
	public Employee(Integer id) {
		this.id = id;
	}
	
	public void answer(Call call) {
		try {
			log.debug(getClass().getSimpleName() + " " + id + " is answering Call " + call.getId());
			Thread.sleep(call.getTime());
			log.debug(getClass().getSimpleName() + " " + id + " has finished Call " + call.getId() + " - Time spent: " + call.getTime() + " milliseconds");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
