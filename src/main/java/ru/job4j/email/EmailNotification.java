package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private static final String SUBJECT = "Notification [%s] to email [%s].";
    private static final String BODY = "Add a new event to [%s].";

    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(() -> send(
                String.format(SUBJECT, user.getUsername(), user.getEmail()),
                String.format(BODY, user.getUsername()),
                user.getEmail()
        ));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}