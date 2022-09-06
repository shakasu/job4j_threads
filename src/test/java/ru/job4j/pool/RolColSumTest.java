package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ru.job4j.pool.RolColSum.asyncSum;
import static ru.job4j.pool.RolColSum.sum;

public class RolColSumTest {
    int[][] matrix;
    Sums[] expected;

    @Before
    public void startUp() {
        matrix = new int[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };

        expected = new Sums[]{
                new Sums(6, 3),
                new Sums(6, 6),
                new Sums(6, 9)
        };
    }

    @Test
    public void async() {
        assertThat(asyncSum(matrix), is(expected));
    }

    @Test
    public void sync() {
        assertThat(sum(matrix), is(expected));
    }
}
