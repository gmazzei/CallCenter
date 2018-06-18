package com.almundo.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.almundo.model.Call;
import com.almundo.model.Consumer;
import com.almundo.model.Producer;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main(String[] args) {
    	BlockingQueue<Call> buffer = new ArrayBlockingQueue<Call>(10, true);
    	
    	Producer producer = new Producer(buffer);
    	Consumer consumer = new Consumer(buffer);
    	new Thread(producer).start();
    	new Thread(consumer).start();
    }
}
