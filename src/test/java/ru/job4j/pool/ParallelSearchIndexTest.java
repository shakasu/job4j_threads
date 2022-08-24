package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParallelSearchIndexTest {

    @Test
    public void whenSizeLess10() {
        var refValue = "refValue";
        var array = new String[]{"", "", "", "", "", "", "", refValue};
        var actualIndex = ParallelSearchIndex.search(array, refValue);

        var expectedIndex = array.length - 1;

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
        var actualIndex = ParallelSearchIndex.search(array, refValue);

        var expectedIndex = 7;

        assertThat(actualIndex, is(expectedIndex));
    }

    @Test
    public void whenInteger() {
        var refValue = 123;
        var array = new Integer[]{1, 2, 3, 4, 4, 4, 1, refValue};
        var actualIndex = ParallelSearchIndex.search(array, refValue);

        var expectedIndex = array.length - 1;

        assertThat(actualIndex, is(expectedIndex));
    }

    @Test
    public void whenBoolean() {
        var refValue = true;
        var array = new Boolean[]{false, false, false, false, false, false, false, refValue};
        var actualIndex = ParallelSearchIndex.search(array, refValue);

        var expectedIndex = array.length - 1;

        assertThat(actualIndex, is(expectedIndex));
    }

    @Test
    public void whenNoContainsRefValue() {
        var refValue = true;
        var array = new Boolean[]{false, false, false, false, false, false, false};
        var actualIndex = ParallelSearchIndex.search(array, refValue);

        var expectedIndex = -1;

        assertThat(actualIndex, is(expectedIndex));
    }
}
