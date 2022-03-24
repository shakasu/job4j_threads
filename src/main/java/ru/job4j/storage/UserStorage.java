package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.List;
import java.util.NoSuchElementException;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private volatile List<User> storage;
    
    public synchronized boolean add(User user) {
        return storage.add(user);
    }
    
    public synchronized boolean update(User user) {
        var result = false;
        for (int userCount = 0; userCount < storage.size(); userCount++) {
            if (storage.get(userCount).getId() == user.getId()) {
                storage.add(new User(storage.get(userCount).getId(), user.getAmount()));
                storage.remove(storage.get(userCount));
                result = true;
                break;
            }
        }
        return result;
    }
    
    public synchronized boolean delete(User user) {
        return storage.remove(user);
    }
    
    public synchronized void transfer(int fromId, int toId, int amount) {
        var newSender = new User(fromId, getUserById(fromId).getAmount() - amount);
        var newReceiver = new User(toId, getUserById(toId).getAmount() + amount);
        update(newSender);
        update(newReceiver);
    }
    
    private synchronized User getUserById(int id) throws NoSuchElementException {
        return storage.stream().filter(u -> u.getId() == id).findFirst().orElseThrow();
    }
}
