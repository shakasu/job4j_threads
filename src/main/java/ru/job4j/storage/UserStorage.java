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
    
    public synchronized User add(User user) {
        return storage.putIfAbsent(user.getId(), user);
    }
    
    public synchronized User update(User user) {
        return storage.replace(user.getId(), user);
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
        var newSender = new User(fromId, sender.getAmount() - amount);
        var newReceiver = new User(toId, receiver.getAmount() + amount);
        update(newSender);
        update(newReceiver);
    }
}
