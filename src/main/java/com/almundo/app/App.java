package com.almundo.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.almundo.model.Call;
import com.almundo.model.Dispatcher;
import com.almundo.model.Producer;


public class App {
	
	private static Logger log = Logger.getLogger(App.class);
	private static final int CALL_QUEUE_MAX_SIZE = 10;
	
    public static void main(String[] args) {
    	log.debug("Call center begins");
    	BlockingQueue<Call> callQueue = new ArrayBlockingQueue<Call>(CALL_QUEUE_MAX_SIZE, true);
    	
    	Producer producer = new Producer(callQueue);
    	Dispatcher dispatcher = new Dispatcher(callQueue);
    	new Thread(producer).start();
    	new Thread(dispatcher).start();
    }
}
