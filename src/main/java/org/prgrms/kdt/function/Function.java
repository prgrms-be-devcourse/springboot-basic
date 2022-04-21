package org.prgrms.kdt.function;

import org.prgrms.kdt.io.OutputConsole;

public enum Function {
    exit(" to exit the program") {
        @Override
        public boolean execute(FunctionOperator functionOperator) {
            new OutputConsole().printExitMessage();
            return isEndProgram;
        }
    },
    create(" to create a new voucher") {
        @Override
        public boolean execute(FunctionOperator functionOperator) {
            functionOperator.execute(this.name());
            return !isEndProgram;
        }
    },
    voucherList(" to list all vouchers") {
        @Override
        public boolean execute(FunctionOperator functionOperator) {
            functionOperator.execute(this.name());
            return !isEndProgram;
        }
    },
    blackList(" to list all blackList") {
        @Override
        public boolean execute(FunctionOperator functionOperator) {
            functionOperator.execute(this.name());
            return !isEndProgram;
        }
    },
    add("to create new customer") {
        @Override
        public boolean execute(FunctionOperator functionOperator) {
            functionOperator.execute(this.name());
            return !isEndProgram;
        }
    };

    private final String explain;

    private final static boolean isEndProgram = true;

    Function(String explain) {
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }

    public abstract boolean execute(FunctionOperator functionOperator);

}
