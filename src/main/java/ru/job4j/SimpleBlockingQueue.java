package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    public final int size;
    
    public SimpleBlockingQueue(int size) {
        this.size = size;
    }
    
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() > size) {
            this.wait();
        }
        queue.offer(value);
        notify();
        System.out.println("offer: " + value);
    }
    
    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        var first = queue.poll();
        notify();
        System.out.println("poll: " + first);
        return first;
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}