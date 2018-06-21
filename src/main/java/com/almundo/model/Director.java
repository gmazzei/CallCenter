package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public class Director extends Position {
	
	public Director(BlockingQueue<Employee> queue) {
		this.name = "Director";
		this.queue = queue;
	}

}
