package com.almundo.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import com.almundo.model.Call;
import com.almundo.model.Dispatcher;
import com.almundo.model.Producer;


public class AppTest extends TestCase {
	
	private static final int CALL_QUEUE_MAX_SIZE = 10;
	private static final int MAX_CONCURRENT_DISPATCHER_TASKS = 10;
	
	/**
	 * Testear que se puedan atender 10 llamadas de forma concurrente.
	 * */
	public void testConcurrentTasks() {
		final int timeout = 25000;
		long start;
		
		BlockingQueue<Call> callQueue = new ArrayBlockingQueue<Call>(CALL_QUEUE_MAX_SIZE, true);
    	
    	Producer producer = new Producer(callQueue);
    	new Thread(producer).start();
    	
    	//Esperamos a que la cola se llene
    	start = System.currentTimeMillis();
    	while (callQueue.size() < CALL_QUEUE_MAX_SIZE) {
    		if (System.currentTimeMillis() - start > timeout) {
    			throw new RuntimeException("Call queue never reached max size. Actual size: " + callQueue.size());
    		} 
    	}
    	
    	Dispatcher dispatcher = new Dispatcher(callQueue);
    	new Thread(dispatcher).start();
    	
    	// Si todo esta bien, el número de hilos 
    	// llegara a 10 en instantes.
    	start = System.currentTimeMillis();
    	while (dispatcher.getBusyTasks() < MAX_CONCURRENT_DISPATCHER_TASKS) {
    		if (System.currentTimeMillis() - start > timeout) {
    			throw new RuntimeException("Busy tasks never reached max number. Actual number: " + dispatcher.getBusyTasks());
    		} 
    	}
    	
    	assertEquals(10, dispatcher.getBusyTasks());
	}
	
	
	/**
	 * Testeo que el Dispatcher este consumiendo las llamadas
	 * de la cola hasta que no quede ninguna.
	 * */
	public void testDispatcher() {
		final int timeout = 25000;
		long start;
		
		BlockingQueue<Call> callQueue = new ArrayBlockingQueue<Call>(CALL_QUEUE_MAX_SIZE, true);
    	
    	Producer producer = new Producer(callQueue);
    	Thread producerThread = new Thread(producer);
    	producerThread.start();
    	
    	//Esperamos a que la cola se llene
    	start = System.currentTimeMillis();
    	while (callQueue.size() < CALL_QUEUE_MAX_SIZE) {
    		if (System.currentTimeMillis() - start > timeout) {
    			throw new RuntimeException("Call queue never reached max size. Actual size: " + callQueue.size());
    		} 
    	}
    	
    	producerThread.interrupt();
    	
    	Dispatcher dispatcher = new Dispatcher(callQueue);
    	Thread dispatcherThread = new Thread(dispatcher);
    	dispatcherThread.start();
    
    	
    	// Si todo esta bien, el dispatcher va a asignar 
    	// todos los pedidos de la cola y esta va a quedar vacia
    	start = System.currentTimeMillis();
    	while (!callQueue.isEmpty()) {
    		if (System.currentTimeMillis() - start > timeout) {
    			throw new RuntimeException("Call queue has not become empty after a long time. Actual queue size: " + callQueue.size());
    		} 
    	}
    	
    	dispatcherThread.interrupt();
    	assertTrue(callQueue.isEmpty());
	}
}
