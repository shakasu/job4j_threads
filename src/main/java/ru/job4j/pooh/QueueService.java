package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.Status.*;

public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        var resp = new Resp("", NOT_IMPLEMENTED);
        var source = req.getSourceName();
        var param = req.getParam();
        var method = req.httpRequestType();
        var currentQueue = queues.get(source);

        if (POST.equals(method)) {
            if (currentQueue == null) {
                var newQueue = new ConcurrentLinkedQueue<String>();
                newQueue.add(param);
                queues.putIfAbsent(source, newQueue);
            } else {
                currentQueue.add(param);
            }
        }
        if (GET.equals(method)) {
            var value = "";
            if (currentQueue != null && !currentQueue.isEmpty()) {
                value = currentQueue.poll();
            }
            resp = new Resp(
                    value,
                    "".equals(value) ? NOT_FOUND : OK
            );
        }

        return resp;
    }
}