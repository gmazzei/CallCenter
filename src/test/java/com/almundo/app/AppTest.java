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
	
	public void testConcurrentTasks() {
		BlockingQueue<Call> callQueue = new ArrayBlockingQueue<Call>(CALL_QUEUE_MAX_SIZE, true);
    	
    	Producer producer = new Producer(callQueue);
    	new Thread(producer).start();
    	
    	while (callQueue.size() < CALL_QUEUE_MAX_SIZE);
    	
    	Dispatcher dispatcher = new Dispatcher(callQueue);
    	new Thread(dispatcher).start();
    	
    	while (dispatcher.getBusyTasks() < 10);
    	
    	assertEquals(10, dispatcher.getBusyTasks());
	}
}
