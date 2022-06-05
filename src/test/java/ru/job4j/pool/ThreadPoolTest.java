package ru.job4j.pool;

import org.junit.Test;

public class ThreadPoolTest {
    private Runnable getRunnable(String name) {
        return () -> {
            System.out.printf("start %s", name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("finish %s", name);
        };
    }
    
    @Test
    public void main() throws InterruptedException {
        var pool = new ThreadPool(10);
        pool.work(getRunnable("first"));
        pool.work(getRunnable("second"));
        pool.shutdown();
    }
}
