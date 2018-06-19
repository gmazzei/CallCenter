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
		return Double.valueOf((Math.random() * 5000 + 5000)).intValue();
	}
	
	public Integer getId() {
		return id;
	} 
	
	public Integer getTime() {
		return time;
	} 
	
}
