package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CacheTest {
    @Test
    public void whenCreateUpdateDelete() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        Base refreshedFirst = new Base(1, 2);
        cache.add(first);
        assertThat(cache.get(1), is(first));
        cache.update(first);
        assertThat(cache.get(1), is(refreshedFirst));
        cache.delete(refreshedFirst);
        assertNull(cache.get(1));
    }
}
