package ru.job4j.pool;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] rsl = new Sums[size];
        for (int i = 0; i < size; i++) {
            rsl[i] = calculateSums(matrix, i);
        }
        return rsl;
    }


    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        var size = matrix.length;
        Sums[] rsl = new Sums[size];
        Map<Integer, Sums> futures = new HashMap<>();
        for (int i = 0; i < size; i++) {
            futures.put(i, getTask(matrix, i).get());
        }

        var iterateIndex = 0;
        for (Integer key : futures.keySet()) {
            rsl[iterateIndex] = futures.get(key);
            iterateIndex++;
        }

        return rsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int fix) {
        return CompletableFuture.supplyAsync(() -> calculateSums(data, fix));
    }

    private static Sums calculateSums(int[][] data, int fix) {
        int colValue = 0;
        int rowValue = 0;
        for (int i = 0; i < data.length; i++) {
            colValue += data[i][fix];
            rowValue += data[fix][i];
        }
        return new Sums(rowValue, colValue);
    }
}