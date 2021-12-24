package ru.job4j.concurrent;

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
                System.out.print("Loading ... |\r");
                Thread.sleep(500);
                System.out.print("Loading ... /\r");
                Thread.sleep(500);
                System.out.print("Loading ... —\r");
                Thread.sleep(500);
                System.out.print("Loading ... \\\r");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}