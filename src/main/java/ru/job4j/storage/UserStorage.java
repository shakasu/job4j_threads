package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private Map<Integer, User> storage;
    
    public synchronized boolean add(User user) {
        storage.put(user.getId(), new User(user.getId(), user.getAmount()));
        return storage.containsValue(user);
    }
    
    public synchronized boolean update(User user) {
        var oldUser = storage.get(user.getId());
        return add(user) && !user.equals(oldUser);
    }
    
    public synchronized boolean delete(User user) {
        storage.remove(user.getId());
        return !storage.containsValue(user);
    }
    
    public synchronized void transfer(int fromId, int toId, int amount) {
        var newSender = new User(fromId, storage.get(fromId).getAmount() - amount);
        var newReceiver = new User(toId, storage.get(toId).getAmount() + amount);
        update(newSender);
        update(newReceiver);
    }
}
