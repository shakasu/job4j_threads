package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = createNewThread();
        Thread second = createNewThread();
        
        another.start();
        second.start();
        System.out.println(Thread.currentThread().getName());
    }
    
    public static Thread createNewThread() {
       return new Thread(
           () -> System.out.println(Thread.currentThread().getName())
       );
    }
}