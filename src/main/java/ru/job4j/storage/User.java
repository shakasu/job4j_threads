package ru.job4j.storage;

public final class User {
    private final int id;
    private final int amount;
    
    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }
    
    public int getId() {
        return id;
    }
    
    public int getAmount() {
        return amount;
    }
}
