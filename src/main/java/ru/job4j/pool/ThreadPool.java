package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final static int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    
    private final SimpleBlockingQueue<Runnable> tasks;
    
    public ThreadPool(int threadPoolSize) {
        tasks = new SimpleBlockingQueue<>(threadPoolSize);
        
        for (int i = 0; i <= POOL_SIZE; i++) {
            Thread thread = new Thread(getTask());
            threads.add(thread);
            thread.start();
        }
    }
    
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }
    
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
    
    private Runnable getTask() {
        return () -> {
            while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                try {
                    tasks.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }
}