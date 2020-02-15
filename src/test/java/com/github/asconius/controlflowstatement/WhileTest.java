package com.github.asconius.controlflowstatement;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.asconius.controlflowstatement.Statement.BREAK;
import static com.github.asconius.controlflowstatement.Statement.CONTINUE;
import static com.github.asconius.controlflowstatement.While.doWhile;
import static com.github.asconius.controlflowstatement.While.whileDo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WhileTest {

    // whileDo

    @Test(expected = NullPointerException.class)
    public void whileDoNull() {
        // given

        // when
        whileDo(null, null).evaluate();

        // then
    }

    @Test
    public void whileDoIncrement() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(1);

        // when
        whileDo(() -> atomicInteger.intValue() < 7,
                () -> {
                    atomicInteger.set(atomicInteger.incrementAndGet());
                    return CONTINUE;
                }).evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void whileDoFalseCondition() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(1);

        // when
        whileDo(() -> atomicInteger.intValue() < 1,
                () -> {
                    atomicInteger.set(atomicInteger.incrementAndGet());
                    return CONTINUE;
                }).evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(1));
    }

    @Test
    public void whileDoBreakCondition() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(1);

        // when
        whileDo(() -> atomicInteger.intValue() < 5,
                () -> {
                    atomicInteger.set(atomicInteger.incrementAndGet());
                    if (atomicInteger.intValue() < 3) {
                        return BREAK;
                    } else {
                        return CONTINUE;
                    }
                }).evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(2));
    }

    // doWhile

    @Test(expected = NullPointerException.class)
    public void doWhileNull() {
        // given

        // when
        doWhile(null, null).evaluate();

        // then
    }

    @Test
    public void doWhileIncrement() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(1);

        // when
        doWhile(() -> atomicInteger.intValue() < 7,
                () -> {
                    atomicInteger.set(atomicInteger.incrementAndGet());
                    return CONTINUE;
                }).evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void doWhileFalseCondition() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(1);

        // when
        doWhile(() -> atomicInteger.intValue() < 1,
                () -> {
                    atomicInteger.set(atomicInteger.incrementAndGet());
                    return CONTINUE;
                }).evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(2));
    }

    @Test
    public void doWhileBreakCondition() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(1);

        // when
        doWhile(() -> atomicInteger.intValue() < 5,
                () -> {
                    atomicInteger.set(atomicInteger.incrementAndGet());
                    if (atomicInteger.intValue() < 3) {
                        return BREAK;
                    } else {
                        return CONTINUE;
                    }
                }).evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(2));
    }
}
