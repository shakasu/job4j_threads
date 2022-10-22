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
        var method = req.httpRequestType();

        if (POST.equals(method)) {
            queues.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queues.get(req.getSourceName()).add(req.getParam());
        } else if (GET.equals(method)) {
            var value = "";
            var currentQueue = queues.getOrDefault(source, new ConcurrentLinkedQueue<>());
            if (!currentQueue.isEmpty()) {
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