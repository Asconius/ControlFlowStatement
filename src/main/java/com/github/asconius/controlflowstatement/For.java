package com.github.asconius.controlflowstatement;

import com.github.asconius.controlflowstatement.intf.ControlFlowStatement;
import com.github.asconius.controlflowstatement.intf.Procedure;

import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

import static com.github.asconius.controlflowstatement.Statement.BREAK;

public final class For<T> implements ControlFlowStatement {

    private final Procedure procedure;

    private For(Iterable<T> iterable, Function<T, Statement> function) {
        procedure = () -> {
            for (T element : iterable) {
                Statement statement = function.apply(element);
                if (BREAK.equals(statement)) {
                    break;
                }
            }
        };
    }

    private For(double start, double end, double step, DoubleFunction<Statement> function) {
        procedure = () -> {
            for (double i = start; i < end; i += step) {
                Statement statement = function.apply(i);
                if (BREAK.equals(statement)) {
                    break;
                }
            }
        };
    }

    private For(int start, int end, int step, IntFunction<Statement> function) {
        procedure = () -> {
            for (int i = start; i < end; i += step) {
                Statement statement = function.apply(i);
                if (BREAK.equals(statement)) {
                    break;
                }
            }
        };
    }

    private For(long start, long end, long step, LongFunction<Statement> function) {
        procedure = () -> {
            for (long i = start; i < end; i += step) {
                Statement statement = function.apply(i);
                if (BREAK.equals(statement)) {
                    break;
                }
            }
        };
    }

    private For(T[] array, Function<T, Statement> function) {
        procedure = () -> {
            for (T element : array) {
                Statement statement = function.apply(element);
                if (BREAK.equals(statement)) {
                    break;
                }
            }
        };
    }

    // Iterable

    public static <T> ControlFlowStatement forEach(Iterable<T> iterable, Function<T, Statement> function) {
        return new For<>(iterable, function);
    }

    // Double

    public static ControlFlowStatement forEach(double end, DoubleFunction<Statement> function) {
        return forEach(0.0, end, function);
    }

    public static ControlFlowStatement forEach(double start, double end, DoubleFunction<Statement> function) {
        return forEach(start, end, 1.0, function);
    }

    public static ControlFlowStatement forEach(double start, double end, double step, DoubleFunction<Statement> function) {
        return new For<>(start, end, step, function);
    }

    // Integer

    public static ControlFlowStatement forEach(int end, IntFunction<Statement> function) {
        return forEach(0, end, function);
    }

    public static ControlFlowStatement forEach(int start, int end, IntFunction<Statement> function) {
        return forEach(start, end, 1, function);
    }

    public static ControlFlowStatement forEach(int start, int end, int step, IntFunction<Statement> function) {
        return new For<>(start, end, step, function);
    }

    // Long

    public static ControlFlowStatement forEach(long end, LongFunction<Statement> function) {
        return forEach(0L, end, function);
    }

    public static ControlFlowStatement forEach(long start, long end, LongFunction<Statement> function) {
        return forEach(start, end, 1L, function);
    }

    public static ControlFlowStatement forEach(long start, long end, long step, LongFunction<Statement> function) {
        return new For<>(start, end, step, function);
    }

    // Array

    public static <T> ControlFlowStatement forEach(T[] array, Function<T, Statement> function) {
        return new For<>(array, function);
    }

    @Override
    public void evaluate() {
        procedure.execute();
    }
}
