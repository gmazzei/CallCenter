package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public class Supervisor extends Position {
	
	public Supervisor(BlockingQueue<Employee> queue) {
		this.name = "Supervisor";
		this.queue = queue;
	}

}
