package ru.job4j.concurrent;

import static ru.job4j.concurrent.ConcurrentOutput.createNewThread;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = createNewThread();
        Thread second = createNewThread();
        
        getNameAndState(first);
        getNameAndState(second);
        
        first.start();
        second.start();
        
        while (first.getState() != Thread.State.TERMINATED) {
            getNameAndState(first);
            getNameAndState(second);
        }
        
        getNameAndState(first);
        getNameAndState(second);
        
        System.out.println("main thread ended");
    }
    
    /**
     * Выводит в консоль имя и состояние переданного треда в параметре.
     * @param thread - тред.
     */
    public static void getNameAndState(Thread thread) {
        System.out.printf("thread: %s, state: %s%n", thread.getName(), thread.getState());
    }
    
}