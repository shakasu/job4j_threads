package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();
    
    public synchronized boolean add(User user) {
        storage.putIfAbsent(user.getId(), new User(user.getId(), user.getAmount()));
        return storage.containsValue(user);
    }
    
    public synchronized boolean update(User user) {
        var oldUser = storage.get(user.getId());
        return !Objects.equals(storage.replace(user.getId(), user), oldUser);
    }
    
    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }
    
    public synchronized void transfer(int fromId, int toId, int amount) {
        var sender = storage.get(fromId);
        var receiver = storage.get(toId);
        if (sender == null || receiver == null) {
            throw new NoSuchElementException();
        }
        if (sender.getAmount() < amount) {
            throw new RuntimeException(String.format("Sender: [%s] not have enough money!", sender.toString()));
        }
        var newSender = new User(fromId, storage.get(fromId).getAmount() - amount);
        var newReceiver = new User(toId, storage.get(toId).getAmount() + amount);
        update(newSender);
        update(newReceiver);
    }
}
