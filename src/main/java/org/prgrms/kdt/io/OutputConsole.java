package org.prgrms.kdt.io;

import org.prgrms.kdt.function.VoucherProgramFunctions;
import org.prgrms.kdt.model.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.List;

public class OutputConsole {
    public void printFunctions() {
        System.out.println("=== Voucher Program ===");
        for (VoucherProgramFunctions function : VoucherProgramFunctions.values()) {
            System.out.println(MessageFormat.format("Type {0}{1}.", function.name(), function.getExplain()));
        }
    }

    public void printVoucherType() {
        System.out.println("=== Type number of voucher you make ===");
        for (VoucherType voucherType : VoucherType.values()) {
            System.out.println(MessageFormat.format("{0} : {1}", voucherType.name(), voucherType.getTypeNumber()));
        }
    }

    public void printList(List<?> printList) {
        printList.forEach(System.out::println);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

}
