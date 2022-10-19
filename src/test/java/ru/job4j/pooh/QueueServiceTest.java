package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    @Test
    public void whenPostThenGetAndGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result1.text(), is(""));
        assertThat(result1.status(), is("203"));
    }

    @Test
    public void whenPostThenGetAndGetTwoQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result1.text(), is(""));
        assertThat(result1.status(), is("203"));

        queueService.process(
                new Req("POST", "queue", "weather1", paramForPostMethod)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather1", null)
        );
        assertThat(result2.text(), is("temperature=18"));
        Resp result3 = queueService.process(
                new Req("GET", "queue", "weather1", null)
        );
        assertThat(result3.text(), is(""));
        assertThat(result3.status(), is("203"));
    }

    @Test
    public void whenQueueServiceTesting() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null));
        assertThat(result.text(), is(""));
    }
}