package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();
    
    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }
    
    public synchronized boolean update(User user) {
        return storage.replace(user.getId(), user) != null;
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
        sender.setAmount(sender.getAmount() - amount);
        receiver.setAmount(receiver.getAmount() + amount);
        update(sender);
        update(receiver);
    }
}
