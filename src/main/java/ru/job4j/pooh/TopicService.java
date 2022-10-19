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
        var currentQueue = queues.get(source);

        if (POST.equals(method)) {
            if (queues.get(source) != null) {
                queues.get(source).values().forEach(e -> e.add(param));
            }
        }
        if (GET.equals(method)) {
            if (queues.get(source) != null && queues.get(source).get(param) != null) {
                var value = "";
                if (!currentQueue.isEmpty()) {
                    value = currentQueue.get(param).poll();
                }
                resp = new Resp(
                        value,
                        "".equals(value) ? NOT_FOUND : OK
                );
            } else {
                queues.putIfAbsent(source, Map.of(param, new ConcurrentLinkedQueue<>()));
            }
        }
        return resp;
    }
}