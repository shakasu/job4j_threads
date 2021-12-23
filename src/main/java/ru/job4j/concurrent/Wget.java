package ru.job4j.concurrent;

import java.util.stream.Stream;

public class Wget {
    public static final int SECOND = 1000;
    public static final int LIMIT = 1000;
    
    public static void main(String[] args) {
        Thread thread = new Thread(
            () -> generate().forEach(progress -> {
                System.out.print("Loading : " + progress  + "%\r");
                try {
                    Thread.sleep(SECOND);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
        );
        thread.start();
    }
    
    private static Stream<Integer> generate() {
        return Stream.iterate(0, i -> i + 1).limit(LIMIT);
    }
}