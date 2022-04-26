package org.prgrms.kdt.function;

import org.prgrms.kdt.io.OutputConsole;
import java.util.function.BiFunction;

public enum VoucherProgramFunctions {
    exit(" to exit the program", (functionOperator, voucherProgramFunctions) -> {
        OutputConsole.printMessage("exit program ! bye :)");
        return isProgramEnd();
    }),
    create(" to create a new voucher", (functionOperator, voucherProgramFunctions) -> {
        functionOperator.execute(voucherProgramFunctions);
        return !isProgramEnd();
    }),
    voucherList(" to list all vouchers", (functionOperator, voucherProgramFunctions) ->{
        functionOperator.execute(voucherProgramFunctions);
        return !isProgramEnd();
    }),
    blackList(" to list all blackList", (functionOperator, voucherProgramFunctions) -> {
        functionOperator.execute(voucherProgramFunctions);
        return !isProgramEnd();
    }),
    add(" to create new customer", (functionOperator, voucherProgramFunctions) -> {
        functionOperator.execute(voucherProgramFunctions);
        return !isProgramEnd();
    }),
    provide(" to provide voucher to customer", (functionOperator, voucherProgramFunctions) -> {
        functionOperator.execute(voucherProgramFunctions);
        return !isProgramEnd();
    }),
    manage(" to list/delete voucher customer has", (functionOperator, voucherProgramFunctions) -> {
        functionOperator.execute(voucherProgramFunctions);
        return !isProgramEnd();
    });

    private final String explain;

    private final BiFunction<FunctionOperator, VoucherProgramFunctions,  Boolean> expression;

    VoucherProgramFunctions(String explain, BiFunction<FunctionOperator, VoucherProgramFunctions, Boolean> expression) {
        this.explain = explain;
        this.expression = expression;
    }

    public String getExplain() {
        return explain;
    }

    public boolean execute(FunctionOperator functionOperator) {
        return expression.apply(functionOperator, this);
    }

    private static boolean isProgramEnd() {
        return true;
    }

}
