package com.github.asconius.controlflowstatement;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.asconius.controlflowstatement.If.ifThen;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IfTest {

    // If

    @Test(expected = NullPointerException.class)
    public void ifNull() {
        // given

        // when
        ifThen(null, null).build().evaluate();

        // then
    }

    @Test
    public void ifTrue() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(6);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void ifFalse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(7);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(8)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    // If Else

    @Test
    public void ifTrueElse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(6);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseThen(() -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void ifElseTrue() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(7);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseThen(() -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(9));
    }

    // If ElseIf

    @Test
    public void ifTrueElseIf() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(6);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void ifElseIfTrue() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(7);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(9));
    }

    @Test
    public void ifElseIf() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(8);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(8));
    }

    // If ElseIf Else

    @Test
    public void ifTrueElseIfElse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(6);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseThen(() -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void ifElseIfTrueElse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(7);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseThen(() -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(8));
    }

    @Test
    public void ifElseIfElseTrue() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(7);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseThen(() -> atomicInteger.set(9)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(8));
    }

    // If ElseIf ElseIf Else

    @Test
    public void ifTrueElseIfElseIfElse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(6);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseIfThen(() -> atomicInteger.intValue() < 9, () -> atomicInteger.set(9)).
                elseThen(() -> atomicInteger.set(10)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(7));
    }

    @Test
    public void ifElseIfTrueElseIfElse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(7);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseIfThen(() -> atomicInteger.intValue() < 9, () -> atomicInteger.set(9)).
                elseThen(() -> atomicInteger.set(10)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(8));
    }

    @Test
    public void ifElseIfElseIfTrueElse() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(8);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseIfThen(() -> atomicInteger.intValue() < 9, () -> atomicInteger.set(9)).
                elseThen(() -> atomicInteger.set(10)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(9));
    }

    @Test
    public void ifElseIfElseIfElseTrue() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger(9);

        // when
        ifThen(() -> atomicInteger.intValue() < 7, () -> atomicInteger.set(7)).
                elseIfThen(() -> atomicInteger.intValue() < 8, () -> atomicInteger.set(8)).
                elseIfThen(() -> atomicInteger.intValue() < 9, () -> atomicInteger.set(9)).
                elseThen(() -> atomicInteger.set(10)).build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(10));
    }
}
