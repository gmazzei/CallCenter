package com.almundo.model;

public class Employee {
	
	private Integer id;
	
	public Employee(Integer id) {
		this.id = id;
	}
	
	public void answer(Call call, int taskId) {
		try {
			System.out.println("Task " + taskId + " - Employee " + id + " - Call " + call.getId() + " - Atendiendo");
			Thread.sleep(call.getTime());
			System.out.println("Task " + taskId + " - Employee " + id + " - Call " + call.getId() + " - Finalizada");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
