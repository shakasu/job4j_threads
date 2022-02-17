package ru.job4j.ref;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();
    
    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }
    
    public User findById(int id) {
        return User.of(users.get(id).getName());
    }
    
    public List<User> findAll() {
        List<User> allUsers = new LinkedList<>();
        for (User user : this.users.values()) {
            var newUser = new User();
            newUser.setId(user.getId());
            newUser.setName(user.getName());
            allUsers.add(newUser);
        }
        return allUsers;
    }
}