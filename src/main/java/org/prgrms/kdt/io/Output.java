package org.prgrms.kdt.io;

import org.prgrms.kdt.model.Function;
import org.prgrms.kdt.model.VoucherType;

import java.text.MessageFormat;

public class Output {
    public void printFunctions() {
        System.out.println("=== Voucher Program ===");
        for (Function function : Function.values()) {
            System.out.println(MessageFormat.format("Type {0}{1}.", function.name(), function.getExplain()));
        }
    }

    public void printInputFunctionError() {
        System.out.println("Type right command");
    }

    public void printVoucherType() {
        System.out.println("Type number of voucher you make");
        for (VoucherType voucherType : VoucherType.values()) {
            System.out.println(MessageFormat.format("{0} : {1}", voucherType.name(), voucherType.getTypeNumber()));
        }
    }
}
