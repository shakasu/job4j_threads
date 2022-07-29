package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] list;
    private final T referenceValue;
    private final int start;
    private final int finish;

    public ParallelSearchIndex(T[] list, T referenceValue, int start, int finish) {
        this.list = list;
        this.referenceValue = referenceValue;
        this.start = start;
        this.finish = finish;
    }

    private static <T> int linearSearch(int start, int finish, T referenceValue, T[] list) {
        if (referenceValue == null) {
            for (int i = start; i < finish; i++) {
                if (list[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < finish; i++) {
                if (referenceValue.equals(list[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        int size = finish - start;

        if (size <= 10) {
            return linearSearch(start, finish, referenceValue, list);
        }

        var mid = size / 2;

        var leftSearch = new ParallelSearchIndex<>(list, referenceValue, start, mid);
        var rightSearch = new ParallelSearchIndex<>(list, referenceValue, mid + 1, finish);

        leftSearch.fork();
        rightSearch.fork();

        var leftValue = leftSearch.join();
        var rightValue = rightSearch.join();

        return leftValue.equals(-1) && !rightValue.equals(-1) ? rightValue : leftValue;
    }
}
