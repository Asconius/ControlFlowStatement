package com.github.asconius.controlflowstatement;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.*;

import static com.github.asconius.controlflowstatement.For.forEach;
import static com.github.asconius.controlflowstatement.Statement.BREAK;
import static com.github.asconius.controlflowstatement.Statement.CONTINUE;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ForTest {

    // Iterable

    @Test(expected = NullPointerException.class)
    public void forEachIterableNull() {
        // given

        // when
        forEach((Iterable<Object>) null, null).evaluate();

        // then
    }

    @Test
    public void forEachCopyList() {
        // given
        final List<String> sourceList = Arrays.asList("a", "b", "c");
        final List<String> destinationList = new ArrayList<>();

        // when
        forEach(sourceList, i -> {
            destinationList.add(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(destinationList, contains("a", "b", "c"));
    }

    @Test
    public void forEachCopyListBreak() {
        // given
        final List<String> sourceList = Arrays.asList("a", "b", "c");
        final List<String> destinationList = new ArrayList<>();

        // when
        forEach(sourceList, i -> {
            destinationList.add(i);
            if ("b".equals(i)) {
                return BREAK;
            } else {
                return CONTINUE;
            }
        }).evaluate();

        // then
        assertThat(destinationList, contains("a", "b"));
    }

    // Long

    @Test(expected = NullPointerException.class)
    public void forEachLongNull() {
        // given

        // when
        forEach(10L, (LongFunction<Statement>) null).evaluate();

        // then
    }

    @Test
    public void forEachCountLongEndArgument() {
        // given
        final AtomicLong atomicLong = new AtomicLong(0L);

        // when
        forEach(10L, (LongFunction<Statement>) i -> {
            atomicLong.set(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(atomicLong.get(), is(9L));
    }

    @Test
    public void forEachCountLongStartEndArgument() {
        // given
        final AtomicLong atomicLong = new AtomicLong(0L);

        // when
        forEach(0L, 10L, (LongFunction<Statement>) i -> {
            atomicLong.set(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(atomicLong.get(), is(9L));
    }

    @Test
    public void forEachCountLongStartEndStepArgument() {
        // given
        final AtomicLong atomicLong = new AtomicLong(0L);

        // when
        forEach(0L, 10L, 1L, (LongFunction<Statement>) i -> {
            atomicLong.set(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(atomicLong.get(), is(9L));
    }

    @Test
    public void forEachCountLongStartEndStepArgumentBreak() {
        // given
        final AtomicLong atomicLong = new AtomicLong(0L);

        // when
        forEach(0L, 10L, 1L, (LongFunction<Statement>) i -> {
            atomicLong.set(i);
            if (atomicLong.get() == 5L) {
                return BREAK;
            } else {
                return CONTINUE;
            }
        }).evaluate();

        // then
        assertThat(atomicLong.get(), is(5L));
    }

    // Integer

    @Test(expected = NullPointerException.class)
    public void forEachIntegerNull() {
        // given

        // when
        forEach(10, (IntFunction<Statement>) null).evaluate();

        // then
    }

    @Test
    public void forEachCountIntegerEndArgument() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(0);

        // when
        forEach(10, (IntFunction<Statement>) i -> {
            atomicInteger.set(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(atomicInteger.get(), is(9));
    }

    @Test
    public void forEachCountIntegerStartEndArgument() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(0);

        // when
        forEach(0, 10, (IntFunction<Statement>) i -> {
            atomicInteger.set(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(atomicInteger.get(), is(9));
    }

    @Test
    public void forEachCountIntegerStartEndStepArgument() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(0);

        // when
        forEach(0, 10, 1, (IntFunction<Statement>) i -> {
            atomicInteger.set(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(atomicInteger.get(), is(9));
    }

    @Test
    public void forEachCountIntegerStartEndStepArgumentBreak() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(0);

        // when
        forEach(0, 10, 1, (IntFunction<Statement>) i -> {
            atomicInteger.set(i);
            if (atomicInteger.get() == 5) {
                return BREAK;
            } else {
                return CONTINUE;
            }
        }).evaluate();

        // then
        assertThat(atomicInteger.get(), is(5));
    }

    @Test
    public void forEachCopyArray() {
        // given
        final Integer[] source = new Integer[] {1, 2, 3};
        final Integer[] destination = new Integer[] {9, 8, 7};

        // when
        forEach(0, source.length, 1, (int i) -> {
            destination[i] = source[i];
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(destination, arrayContaining(1, 2, 3));
    }

    // Double

    @Test(expected = NullPointerException.class)
    public void forEachDoubleNull() {
        // given

        // when
        forEach(10.0, null).evaluate();

        // then
    }

    @Test
    public void forEachCountDoubleEndArgument() {
        // given
        final DoubleAdder doubleAdder = new DoubleAdder();

        // when
        forEach(10.0, i -> {
            doubleAdder.add(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(doubleAdder.sum(), is(45.0));
    }

    @Test
    public void forEachCountDoubleStartEndArgument() {
        // given
        final DoubleAdder doubleAdder = new DoubleAdder();

        // when
        forEach(0.0, 10.0, i -> {
            doubleAdder.add(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(doubleAdder.sum(), is(45.0));
    }

    @Test
    public void forEachCountDoubleStartEndStepArgument() {
        // given
        final DoubleAdder doubleAdder = new DoubleAdder();

        // when
        forEach(0.0, 10.0, 1.0, i -> {
            doubleAdder.add(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(doubleAdder.sum(), is(45.0));
    }

    @Test
    public void forEachCountDoubleStartEndStepArgumentBreak() {
        // given
        final DoubleAdder doubleAdder = new DoubleAdder();

        // when
        forEach(0.0, 10.0, 1.0, i -> {
            doubleAdder.add(i);
            if (doubleAdder.doubleValue() == 15.0) {
                return BREAK;
            } else {
                return CONTINUE;
            }
        }).evaluate();

        // then
        assertThat(doubleAdder.sum(), is(15.0));
    }

    // Array

    @Test(expected = NullPointerException.class)
    public void forEachArrayNull() {
        // given

        // when
        forEach((Object[]) null, null).evaluate();

        // then
    }

    @Test
    public void forEachFillList() {
        // given
        final String[] array = new String[] {"a", "b", "c"};
        final List<String> destinationList = new ArrayList<>();

        // when
        forEach(array, i -> {
            destinationList.add(i);
            return CONTINUE;
        }).evaluate();

        // then
        assertThat(destinationList, contains("a", "b", "c"));
    }

    @Test
    public void forEachFillListBreak() {
        // given
        final String[] array = new String[] {"a", "b", "c"};
        final List<String> destinationList = new ArrayList<>();

        // when
        forEach(array, i -> {
            destinationList.add(i);
            if ("b".equals(i)) {
                return BREAK;
            } else {
                return CONTINUE;
            }
        }).evaluate();

        // then
        assertThat(destinationList, contains("a", "b"));
    }
}
