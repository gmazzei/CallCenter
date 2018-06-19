package com.almundo.model;

public class Call {
	
	private final Integer id;
	private final Integer time; 
	
	public Call(Integer id, Integer time) {
		this.id = id;
		this.time = time;
	}
	
	public Integer getId() {
		return id;
	} 
	
	public Integer getTime() {
		return time;
	} 
	
}
