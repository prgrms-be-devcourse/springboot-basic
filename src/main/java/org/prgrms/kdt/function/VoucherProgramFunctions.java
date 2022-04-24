package org.prgrms.kdt.function;

import org.prgrms.kdt.io.OutputConsole;
import java.util.function.BiFunction;

public enum VoucherProgramFunctions {
    exit(" to exit the program", (functionOperator, functionName) -> {
        OutputConsole.printMessage("exit program ! bye :)");
        return true;
    }),
    create(" to create a new voucher", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return false;
    }),
    voucherList(" to list all vouchers", (functionOperator, functionName) ->{
        functionOperator.execute(functionName);
        return false;
    }),
    blackList(" to list all blackList", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return false;
    }),
    add(" to create new customer", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return false;
    }),
    provide(" to provide voucher to customer", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return false;
    }),
    manage(" to list/delete voucher customer has", (functionOperator, functionName) -> {
        functionOperator.execute(functionName);
        return false;
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
        return  expression.apply(functionOperator, this.name());
    }

}
