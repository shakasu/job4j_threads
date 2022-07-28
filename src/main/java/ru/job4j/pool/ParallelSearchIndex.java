package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] list;
    private final T referenceValue;

    private final int additionCounter;

    public ParallelSearchIndex(T[] list, T referenceValue, int additionCounter) {
        this.list = list;
        this.referenceValue = referenceValue;
        this.additionCounter = additionCounter;
    }

    private int linearSearch(T[] currentList, int additionCounter) {
        if (referenceValue == null) {
            for (int i = 0; i < currentList.length; i++) {
                if (currentList[i] == null) {
                    return i + additionCounter;
                }
            }
        } else {
            for (int i = 0; i < currentList.length; i++) {
                if (referenceValue.equals(currentList[i])) {
                    return i + additionCounter;
                }
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        int size = list.length;

        if (size <= 10) {
            return linearSearch(list, additionCounter);
        } else {
            var mid = size / 2;

            var leftSearch = new ParallelSearchIndex<>(Arrays.copyOfRange(list, 0, mid), referenceValue, mid + 1);
            var rightSearch = new ParallelSearchIndex<>(Arrays.copyOfRange(list, mid + 1, list.length - 1), referenceValue, mid + 1);

            leftSearch.fork();
            rightSearch.fork();

            var leftValue = leftSearch.join();
            var rightValue = rightSearch.join();

            return leftValue.equals(-1) && !rightValue.equals(-1) ? rightValue : leftValue;
        }
    }
}
