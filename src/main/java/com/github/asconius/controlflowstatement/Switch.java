package com.github.asconius.controlflowstatement;

import com.github.asconius.controlflowstatement.intf.Buildable;
import com.github.asconius.controlflowstatement.intf.ControlFlowStatement;
import com.github.asconius.controlflowstatement.intf.Procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Switch<T> implements ControlFlowStatement {

    private final T value;
    private final List<Procedure> procedureList = new ArrayList<>();
    private boolean locked = true;

    private Switch(T value) {
        this.value = value;
    }

    public static <T> Case<T> switchCase(T value) {
        return new Builder<>(value);
    }

    @Override
    public void evaluate() {
        procedureList.forEach(Procedure::execute);
    }

    public interface Case<T> extends Buildable<ControlFlowStatement> {
        Case<T> breakCase(T value, Procedure procedure);
        Case<T> continueCase(T value, Procedure procedure);
        Buildable<ControlFlowStatement> defaultCase(Procedure procedure);
    }

    private static final class Builder<T> implements Case<T> {

        private final Switch<T> switchStatement;

        private Builder(T value) {
            switchStatement = new Switch<>(value);
        }

        @Override
        public Case<T> breakCase(T value, Procedure procedure) {
            addProcedure(Objects.equals(switchStatement.value, value), true, procedure);
            return this;
        }

        @Override
        public Case<T> continueCase(T value, Procedure procedure) {
            addProcedure(Objects.equals(switchStatement.value, value), false, procedure);
            return this;
        }

        @Override
        public Buildable<ControlFlowStatement> defaultCase(Procedure procedure) {
            addProcedure(switchStatement.procedureList.isEmpty(), true, procedure);
            return this;
        }

        @Override
        public ControlFlowStatement build() {
            return switchStatement;
        }

        private void addProcedure(boolean condition, boolean locked, Procedure procedure) {
            if (!switchStatement.locked || condition) {
                switchStatement.procedureList.add(procedure);
                switchStatement.locked = locked;
            }
        }
    }
}
