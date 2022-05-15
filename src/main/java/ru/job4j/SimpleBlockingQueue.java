package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    public static final int SIZE = 10;
    
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    
    public synchronized void offer(T value) {
        while (queue.size() > SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        notify();
        System.out.println("offer: " + value);
    }
    
    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        var first = queue.poll();
        notify();
        System.out.println("poll: " + first);
        return first;
    }
}