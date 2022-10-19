package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.Status.*;

public class TopicService implements Service {
    private final Map<String, Map<String, ConcurrentLinkedQueue<String>>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        var resp = new Resp("", OK);
        var source = req.getSourceName();
        var param = req.getParam();
        var currentQueue = queues.get(source);

        if (POST.equals(req.httpRequestType())) {
            if (isTopicExist(source)) {
                for (String sub : queues.get(source).keySet()) {
                    currentQueue.get(sub).add(param);
                }
            }
        } else {
            if (isTopicExist(source) && isUserSubscribed(source, param)) {
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
        if (isMethodUnavailable(req.httpRequestType())) {
            resp = new Resp("", NOT_IMPLEMENTED);
        }
        return resp;
    }

    private boolean isTopicExist(String topic) {
        return queues.containsKey(topic);
    }

    private boolean isUserSubscribed(String topic, String user) {
        return queues.get(topic).containsKey(user);
    }
}