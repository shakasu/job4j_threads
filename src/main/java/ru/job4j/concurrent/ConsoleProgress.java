package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;

public class ConsoleProgress implements Runnable {
    private final List<String> SYMBOLS = Arrays.asList("|", "/", "—", "\\");
    
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String symbol : SYMBOLS) {
                    System.out.print("Loading ... " + symbol + "\r");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}