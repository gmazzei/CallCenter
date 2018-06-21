package com.almundo.model;

public class Call {
	
	private final Integer id;
	
	/* Time in Milliseconds */
	private final Integer time;
	
	
	public Call(Integer id) {
		this.id = id;
		this.time = generateRandomTime();
	}
	
	private Integer generateRandomTime() {
		return (int) (Math.random() * 5000 + 5000);
	}
	
	public Integer getId() {
		return id;
	} 
	
	public Integer getTime() {
		return time;
	} 
	
}
