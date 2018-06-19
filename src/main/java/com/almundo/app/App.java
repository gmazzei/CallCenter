package com.almundo.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.almundo.model.Call;
import com.almundo.model.Dispatcher;
import com.almundo.model.Producer;


public class App {
	
	private static final int BUFFER_MAX_SIZE = 10;
	
    public static void main(String[] args) {
    	BlockingQueue<Call> buffer = new ArrayBlockingQueue<Call>(BUFFER_MAX_SIZE, true);
    	
    	Producer producer = new Producer(buffer);
    	Dispatcher dispatcher = new Dispatcher(buffer);
    	new Thread(producer).start();
    	new Thread(dispatcher).start();
    }
}
