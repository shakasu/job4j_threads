package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    
    private static final long INITIAL_PAUSE = 1000;
    private static final int BYTE = 1024;
    
    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }
    
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("downloaded.json")) {
            byte[] dataBuffer = new byte[BYTE];
            int bytesRead;
            var currentTime = System.currentTimeMillis();
            
            while ((bytesRead = in.read(dataBuffer, 0, BYTE)) != -1) {
                System.out.printf("buffer: %d%n", bytesRead);
                var deltaTime = System.currentTimeMillis() - currentTime;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                
                Thread.sleep(sleepCalculation(bytesRead, deltaTime));
                
                currentTime = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private long sleepCalculation(int sizeData, long deltaTime) {
        var pause = INITIAL_PAUSE;
        
        if (deltaTime != 0) {
            var actualSpeed = sizeData / deltaTime;
            if (actualSpeed > speed) {
                pause = sizeData / (actualSpeed - speed);
            } else {
                pause = 0;
            }
        }
        
        System.out.printf("pause: %d%n", pause);
        return pause;
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}