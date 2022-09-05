package ru.job4j.pool;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] rsl = new Sums[size];
        for (int i = 0; i < size; i++) {
            int rowValue = 0;
            int colValue = 0;
            for (int j = 0; j < size; j++) {
                colValue += matrix[j][i];
                rowValue += matrix[i][j];
            }
            rsl[i] = new Sums(rowValue, colValue);
        }
        return rsl;
    }


    public static Sums[] asyncSum(int[][] matrix) {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }

        return futures.keySet().stream()
                .map(k -> {
                    try {
                        return futures.get(k).get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Sums[]::new);
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int fix) {
        return CompletableFuture.supplyAsync(() -> {
            int colValue = 0;
            int rowValue = 0;
            for (int i = 0; i < data.length; i++) {
                colValue += data[i][fix];
                rowValue += data[fix][i];
            }
            return new Sums(rowValue, colValue);
        });
    }
}