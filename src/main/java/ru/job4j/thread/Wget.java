package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;
//"https://raw.githubusercontent.com/shakasu/job4j_threads/master/download_me.json" 100
//"https://proof.ovh.net/files/10Mb.dat" 1048576

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    
    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }
    
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             var ignored = new FileOutputStream(new LinkedList<>(Arrays.asList(url.split("/"))).getLast())) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            var initTime = System.currentTimeMillis();
            var dataCount = 0L;
            
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                System.out.printf("buffer: %d%n", bytesRead);
                dataCount = dataCount + bytesRead;
                System.out.printf("data count: %d%n", dataCount);
                if (dataCount >= speed) {
                    var deltaTime = System.currentTimeMillis() - initTime;
                    System.out.printf("delta time: %d%n", deltaTime);
                    initTime = System.currentTimeMillis();
                    dataCount = 0;
                    if (deltaTime < 1000) {
                        var sleep = 1000 - deltaTime;
                        System.out.printf("sleep: %d%n", sleep);
                        Thread.sleep(sleep);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                String.format("Invalid parameters: it [%d], should be [2]", args.length)
            );
        }
        if (!args[0].startsWith("https://")) {
            throw new IllegalArgumentException(String.format("Bad url: [%s]", args[0]));
        }
        if (!Pattern.compile("[0-9]+").matcher(args[1]).matches()) {
            throw new IllegalArgumentException(String.format("Invalid speed: [%s]", args[1]));
        }
        return true;
    }
    
    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}