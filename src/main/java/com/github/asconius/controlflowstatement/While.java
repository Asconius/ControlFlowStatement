package com.github.asconius.controlflowstatement;

import com.github.asconius.controlflowstatement.intf.ControlFlowStatement;
import com.github.asconius.controlflowstatement.intf.Procedure;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static com.github.asconius.controlflowstatement.Statement.CONTINUE;

public final class While implements ControlFlowStatement {

    private final Procedure procedure;

    private While(Procedure procedure) {
        this.procedure = procedure;
    }

    public static ControlFlowStatement whileDo(BooleanSupplier condition, Supplier<Statement> supplier) {
        return new While(() -> {
            Statement statement = CONTINUE;
            while (condition.getAsBoolean() && CONTINUE.equals(statement)) {
                statement = supplier.get();
            }
        });
    }

    public static ControlFlowStatement doWhile(BooleanSupplier condition, Supplier<Statement> supplier) {
        return new While(() -> {
            Statement statement;
            do {
                statement = supplier.get();
            } while (condition.getAsBoolean() && CONTINUE.equals(statement));
        });
    }

    @Override
    public void evaluate() {
        procedure.execute();
    }
}
