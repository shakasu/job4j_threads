package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.Status.*;

public class TopicService implements Service {
    private final Map<String, Map<String, ConcurrentLinkedQueue<String>>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        var resp = new Resp("", NOT_IMPLEMENTED);
        var source = req.getSourceName();
        var param = req.getParam();
        var method = req.httpRequestType();

        if (POST.equals(method)) {
            if (queues.get(source) != null) {
                queues.get(source).values().forEach(e -> e.add(param));
            }
        } else if (GET.equals(method)) {
            queues.putIfAbsent(source, new ConcurrentHashMap<>());
            queues.get(source).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            var currentQueue = queues.get(source).get(req.getParam());
            var value = "";
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