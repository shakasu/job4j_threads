package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.pooh.Req.POST;
import static ru.job4j.pooh.Resp.Status.NOT_FOUND;
import static ru.job4j.pooh.Resp.Status.OK;

public class TopicService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();
    public static final String SPLITTER = "...111...";

    @Override
    public Resp process(Req req) {
        var resp = new Resp("", OK);
        var source = req.getSourceName();
        var param = req.getParam();
        var subscribedTopic = concatenateKey(param, source);
        var currentQueue = queues.get(subscribedTopic);

        if (POST.equals(req.httpRequestType())) {
            if (isTopicExist(source)) {
                for (String key : queues.keySet()) {
                    if (key.startsWith(source.concat(SPLITTER))) {
                        queues.get(key).add(param);
                    }
                }
            }
        } else {
            if (isTopicExist(source)) {
                if (isUserSubscribed(source, param)) {
                    var value = currentQueue.poll();
                    resp = new Resp(
                            value == null ? "" : value,
                            value == null ? NOT_FOUND : OK
                    );
                }

            } else {
                var newQueue = new ConcurrentLinkedQueue<String>();
                queues.putIfAbsent(subscribedTopic, newQueue);
            }
        }

        return resp;
    }

    private static String concatenateKey(String consumer, String topic) {
        return topic.concat(SPLITTER).concat(consumer);
    }

    private boolean isTopicExist(String topic) {
        var res = false;
        for (String key : queues.keySet()) {
            if (key.split(SPLITTER)[0].equals(topic)) {
                res = true;
                break;
            }
        }
        return res;
    }

    private boolean isUserSubscribed(String topic, String user) {
        var res = false;
        for (String key : queues.keySet()) {
            if (key.equals(concatenateKey(user, topic))) {
                res = true;
            }
        }
        return res;
    }
}