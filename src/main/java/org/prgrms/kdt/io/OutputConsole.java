package org.prgrms.kdt.io;

import org.prgrms.kdt.model.function.Function;
import org.prgrms.kdt.model.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.List;

public class OutputConsole implements Output {
    @Override
    public void printFunctions() {
        System.out.println("=== Voucher Program ===");
        for (Function function : Function.values()) {
            System.out.println(MessageFormat.format("Type {0}{1}.", function.name(), function.getExplain()));
        }
    }

    @Override
    public void printInputFunctionError() {
        System.out.println("WRONG : Type right command\n");
    }

    @Override
    public void printVoucherType() {
        System.out.println("=== Type number of voucher you make ===");
        for (VoucherType voucherType : VoucherType.values()) {
            System.out.println(MessageFormat.format("{0} : {1}", voucherType.name(), voucherType.getTypeNumber()));
        }
    }

    @Override
    public void printVoucherListEmptyError() {
        System.out.println("voucher list is empty !!\n");
    }

    @Override
    public void printExitMessage() {
        System.out.println("exit program ! bye :)");
    }

    @Override
    public void printExceptionMessage(String exceptionMessage) {
        System.out.println(MessageFormat.format("{0}\n", exceptionMessage));
    }

    public void printList(List<String> printList) {
        for(String argument : printList) {
            System.out.println(argument);
        }
    }
}
