package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParallelSearchIndexTest {

    @Test
    public void whenSizeLess10() {
        var refValue = "refValue";
        var array = new String[]{"", "", "", "", "", "", "", refValue};
        var stringParallelSearchIndex = new ParallelSearchIndex<>(array, refValue, 0, array.length);

        var expectedIndex = array.length - 1;

        var actualIndex = stringParallelSearchIndex.compute();
        assertThat(actualIndex, is(expectedIndex));
    }

    @Test
    public void whenSizeGreater10() {
        var refValue = "refValue";
        var array = new String[]{
                "", "", "", "", "", "", "", refValue,
                "", "", "", "", "", "", "",
                "", "", "", "", "", "", "",
        };
        var stringParallelSearchIndex = new ParallelSearchIndex<>(array, refValue, 0, array.length);

        var expectedIndex = 7;

        var actualIndex = stringParallelSearchIndex.compute();
        assertThat(actualIndex, is(expectedIndex));
    }
}
