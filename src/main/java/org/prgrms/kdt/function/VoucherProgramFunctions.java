package org.prgrms.kdt.function;

import org.prgrms.kdt.io.OutputConsole;
import java.util.function.BiFunction;

public enum VoucherProgramFunctions {
    exit(" to exit the program", (functionOperator, functionName) -> {
        OutputConsole.printMessage("exit program ! bye :)");
        return isProgramEnd();
    }),
    create(" to create a new voucher", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return !isProgramEnd();
    }),
    voucherList(" to list all vouchers", (functionOperator, functionName) ->{
        functionOperator.execute(functionName);
        return !isProgramEnd();
    }),
    blackList(" to list all blackList", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return !isProgramEnd();
    }),
    add(" to create new customer", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return !isProgramEnd();
    }),
    provide(" to provide voucher to customer", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return !isProgramEnd();
    }),
    manage(" to list/delete voucher customer has", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return !isProgramEnd();
    });

    private final String explain;

    private final BiFunction<FunctionOperator, String,  Boolean> expression;

    VoucherProgramFunctions(String explain, BiFunction<FunctionOperator, String, Boolean> expression) {
        this.explain = explain;
        this.expression = expression;
    }

    public String getExplain() {
        return explain;
    }

    public boolean execute(FunctionOperator functionOperator) {
        return expression.apply(functionOperator, this.name());
    }

    private static boolean isProgramEnd() {
        return true;
    }

}
