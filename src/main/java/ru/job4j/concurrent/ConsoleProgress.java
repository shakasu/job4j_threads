package ru.job4j.concurrent;

import java.util.Arrays;

public class ConsoleProgress implements Runnable {
    
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
                for (String symbol : Arrays.asList("|", "/", "—", "\\")) {
                    System.out.print("Loading ... " + symbol + "\r");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}