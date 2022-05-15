package ru.job4j;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {
    @Test
    public void add() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>();
        Thread producer = new Thread(() -> {
            for (Integer i : Stream.iterate(0, i -> i + 1).limit(10).collect(Collectors.toList())) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                queue.poll();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll(), is(9));
    }
}
