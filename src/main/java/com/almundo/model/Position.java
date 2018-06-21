package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public abstract class Position {
	
	protected String name;
	protected BlockingQueue<Employee> queue;
	
	public String getName() {
		return name;
	}
	
	public BlockingQueue<Employee> getOwnQueue() {
		return queue;
	}
}
