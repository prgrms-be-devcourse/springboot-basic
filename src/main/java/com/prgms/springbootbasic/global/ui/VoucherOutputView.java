package com.prgms.springbootbasic.global.ui;

import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

@Component
public class VoucherOutputView {

    private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();

    public void initApplication() {
        textTerminal.println("=== Voucher Application ===");
        textTerminal.println("Type create to create a new voucher");
        textTerminal.println("Type list to list all vouchers");
    }

    public void showWhenEntervoucherType() {
        textTerminal.println("\nType fixed to create a new fixed voucher.");
        textTerminal.println("Type percent to create a new percent voucher.");
    }

    public void showWhenEntervoucherNumber() {
        textTerminal.println("\nNumber of voucher's amount or percent.");
    }


}
