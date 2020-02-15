package com.github.asconius.controlflowstatement;

import com.github.asconius.controlflowstatement.intf.Buildable;
import com.github.asconius.controlflowstatement.intf.ControlFlowStatement;
import com.github.asconius.controlflowstatement.intf.Procedure;

import java.util.function.BooleanSupplier;

public final class If implements ControlFlowStatement {

    private Procedure procedure;

    private If() { }

    @Override
    public void evaluate() {
        if (hasProcedure()) {
            procedure.execute();
        }
    }

    public static Else ifThen(BooleanSupplier condition, Procedure procedure) {
        return new Builder(condition, procedure);
    }

    private boolean hasProcedure() {
        return procedure != null;
    }

    public interface Else extends Buildable<ControlFlowStatement> {
        Else elseIfThen(BooleanSupplier condition, Procedure procedure);
        Buildable<ControlFlowStatement> elseThen(Procedure procedure);
    }

    private static final class Builder implements Else {

        private final If ifStatement = new If();

        private Builder(BooleanSupplier condition, Procedure procedure) {
            setProcedure(condition.getAsBoolean(), procedure);
        }

        @Override
        public Else elseIfThen(BooleanSupplier condition, Procedure procedure) {
            setProcedure(!ifStatement.hasProcedure() && condition.getAsBoolean(), procedure);
            return this;
        }

        @Override
        public Buildable<ControlFlowStatement> elseThen(Procedure procedure) {
            setProcedure(!ifStatement.hasProcedure(), procedure);
            return this;
        }

        @Override
        public ControlFlowStatement build() {
            return ifStatement;
        }

        private void setProcedure(boolean condition, Procedure procedure) {
            ifStatement.procedure = condition ? procedure : ifStatement.procedure;
        }
    }
}
