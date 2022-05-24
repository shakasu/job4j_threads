package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();
    
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }
    
    public boolean update(Base model) {
        return memory.computeIfPresent(
            model.getId(),
            (key, value) -> {
                Base stored = memory.get(model.getId());
                if (stored.getVersion() != model.getVersion()) {
                    throw new RuntimeException("Versions are not equal");
                }
                return new Base(model.getId(), model.getVersion() + 1);
            }) != null;
    }
    
    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }
    
    public Base get(int id) {
        return memory.get(id);
    }
}