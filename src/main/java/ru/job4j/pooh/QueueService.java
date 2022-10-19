package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.Status.*;

public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        var resp = new Resp("", OK);
        var source = req.getSourceName();
        var param = req.getParam();
        var currentQueue = queues.get(source);

        if (POST.equals(req.httpRequestType())) {
            if (currentQueue == null) {
                var newQueue = new ConcurrentLinkedQueue<String>();
                newQueue.add(param);
                queues.putIfAbsent(source, newQueue);
            } else {
                currentQueue.add(param);
            }
        } else {
            var value = "";
            if (!currentQueue.isEmpty()) {
                value = currentQueue.poll();
            }
            resp = new Resp(
                    value,
                    "".equals(value) ? NOT_FOUND : OK
            );
        }

        if (isMethodUnavailable(req.httpRequestType())) {
            resp = new Resp("", NOT_IMPLEMENTED);
        }

        return resp;
    }
}