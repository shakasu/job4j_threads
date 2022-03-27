package ru.job4j.synch;

import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;
    
    public SingleLockList() {
        this.list = new LinkedList<>();
    }
    
    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }
    
    public synchronized void add(T value) {
        list.add(value);
        new SingleLockList<T>(list);
    }
    
    public synchronized T get(int index) {
        return list.get(index);
    }
    
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
    
    private synchronized List<T> copy(List<T> list) {
        return List.copyOf(list);
    }
}