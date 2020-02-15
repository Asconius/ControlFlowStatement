package com.github.asconius.controlflowstatement;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.asconius.controlflowstatement.Switch.switchCase;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SwitchTest {

    @Test
    public void switchValueNull() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger();

        // when
        switchCase(null).
                breakCase(new Object(), () -> atomicInteger.set(1)).
                breakCase(null, () -> atomicInteger.set(2)).
                build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(2));
    }

    @Test(expected = NullPointerException.class)
    public void switchValueProcedureNull() {
        // given

        // when
        switchCase(null).breakCase(null, null).build().evaluate();

        // then
    }

    @Test
    public void switchJanuary() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger();
        String month = "January";

        // when
        switchCase(month).
                breakCase("January", () -> atomicInteger.set(1)).
                breakCase("February", () -> atomicInteger.set(2)).
                breakCase("March", () -> atomicInteger.set(3)).
                breakCase(null, () -> atomicInteger.set(4)).
                build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(1));
    }

    @Test
    public void switchFebruary() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger();
        String month = "February";

        // when
        switchCase(month).
                breakCase("January", () -> atomicInteger.set(1)).
                breakCase("February", () -> atomicInteger.set(2)).
                breakCase("March", () -> atomicInteger.set(3)).
                defaultCase(() -> atomicInteger.set(4)).
                build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(2));
    }

    @Test
    public void switchMarch() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger();
        String month = "March";

        // when
        switchCase(month).
                breakCase("January", () -> atomicInteger.set(1)).
                breakCase("February", () -> atomicInteger.set(2)).
                breakCase("March", () -> atomicInteger.set(3)).
                build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(3));
    }

    @Test
    public void switchApril() {
        // given
        final AtomicInteger atomicInteger = new AtomicInteger();
        String month = "April";

        // when
        switchCase(month).
                breakCase("January", () -> atomicInteger.set(1)).
                breakCase("February", () -> atomicInteger.set(2)).
                breakCase("March", () -> atomicInteger.set(3)).
                defaultCase(() -> atomicInteger.set(4)).
                build().evaluate();

        // then
        assertThat(atomicInteger.intValue(), is(4));
    }

    @Test
    public void switchContinue() {
        // given
        final List<String> monthList = Arrays.asList("January", "February", "March");
        final List<Integer> numberList = new ArrayList<>();

        // when
        switchCase(monthList.size()).
                continueCase(4, () -> numberList.add(4)).
                continueCase(3, () -> numberList.add(3)).
                continueCase(2, () -> numberList.add(2)).
                continueCase(1, () -> numberList.add(1)).
                build().evaluate();

        // then
        assertThat(numberList, contains(3, 2, 1));
    }

    @Test
    public void switchContinueBreak() {
        // given
        final List<String> monthList = Arrays.asList("January", "February", "March");
        final List<Integer> numberList = new ArrayList<>();

        // when
        switchCase(monthList.size()).
                continueCase(4, () -> numberList.add(4)).
                continueCase(3, () -> numberList.add(3)).
                breakCase(2, () -> numberList.add(2)).
                continueCase(1, () -> numberList.add(1)).
                build().evaluate();

        // then
        assertThat(numberList, contains(3, 2));
    }

    @Test
    public void switchContinueBreakContinueBreak() {
        // given
        final List<String> monthList = Arrays.asList("January", "February", "March");
        final List<Integer> numberList = new ArrayList<>();

        // when
        switchCase(monthList.size()).
                continueCase(3, () -> numberList.add(3)).
                breakCase(2, () -> numberList.add(2)).
                continueCase(1, () -> numberList.add(1)).
                continueCase(3, () -> numberList.add(3)).
                breakCase(2, () -> numberList.add(2)).
                build().evaluate();

        // then
        assertThat(numberList, contains(3, 2, 3, 2));
    }

    @Test
    public void switchContinueBreakContinueBreakDefault() {
        // given
        final List<String> monthList = Arrays.asList("January", "February", "March");
        final List<Integer> numberList = new ArrayList<>();

        // when
        switchCase(monthList.size()).
                continueCase(3, () -> numberList.add(3)).
                breakCase(2, () -> numberList.add(2)).
                continueCase(1, () -> numberList.add(1)).
                continueCase(3, () -> numberList.add(3)).
                breakCase(2, () -> numberList.add(2)).
                defaultCase(() -> numberList.add(1)).
                build().evaluate();

        // then
        assertThat(numberList, contains(3, 2, 3, 2));
    }

    @Test
    public void switchContinueBreakContinueDefault() {
        // given
        final List<String> monthList = Arrays.asList("January", "February", "March");
        final List<Integer> numberList = new ArrayList<>();

        // when
        switchCase(monthList.size()).
                continueCase(3, () -> numberList.add(3)).
                breakCase(2, () -> numberList.add(2)).
                continueCase(1, () -> numberList.add(1)).
                continueCase(3, () -> numberList.add(3)).
                defaultCase(() -> numberList.add(2)).
                build().evaluate();

        // then
        assertThat(numberList, contains(3, 2, 3, 2));
    }

    @Test
    public void switchDefault() {
        // given
        final List<String> monthList = Arrays.asList("January", "February", "March");
        final List<Integer> numberList = new ArrayList<>();

        // when
        switchCase(monthList.size()).
                defaultCase(() -> numberList.add(2)).
                build().evaluate();

        // then
        assertThat(numberList, contains(2));
    }
}
