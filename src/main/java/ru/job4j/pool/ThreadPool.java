package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final static List<Thread> THREADS = new LinkedList<>();
    private final static int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    
    private final SimpleBlockingQueue<Runnable> tasks;
    
    public ThreadPool(int threadPoolSize) throws InterruptedException {
        tasks = new SimpleBlockingQueue<>(threadPoolSize);
        
        while (!isAllStopped()) {
            removeTerminatedThreads();
            
            if (THREADS.size() < POOL_SIZE) {
                Thread thread = new Thread(tasks.poll());
                THREADS.add(thread);
                thread.start();
            }
        }
    }
    
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }
    
    public void shutdown() {
        THREADS.forEach(Thread::interrupt);
    }
    
    private boolean isAllStopped() {
        for (Thread thread : THREADS) {
            if (!thread.isInterrupted()) {
                return false;
            }
        }
        return true;
    }
    
    private void removeTerminatedThreads() {
        List<Thread> terminatedList = new LinkedList<>();
        for (Thread thread : THREADS) {
            if (Thread.State.TERMINATED.equals(thread.getState())) {
                terminatedList.add(thread);
            }
        }
        THREADS.removeAll(terminatedList);
    }
}