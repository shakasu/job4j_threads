package ru.job4j.pooh;

import static ru.job4j.pooh.Req.AVAILABLE_METHODS;

public interface Service {
    Resp process(Req req);

     default boolean isMethodUnavailable(String method) {
        return !AVAILABLE_METHODS.contains(method);
    }
}