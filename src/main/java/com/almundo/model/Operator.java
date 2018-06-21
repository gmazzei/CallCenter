package com.almundo.model;

import java.util.concurrent.BlockingQueue;

public class Operator extends Position {
	
	public Operator(BlockingQueue<Employee> queue) {
		this.name = "Operator";
		this.queue = queue;
	}

}
