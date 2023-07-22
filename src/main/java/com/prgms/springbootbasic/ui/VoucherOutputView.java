package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.util.VoucherType;
import com.prgms.springbootbasic.domain.Voucher;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherOutputView {

    private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();
    private static final String FORMAT_FIXED = "voucher type : %s voucher Id : %s amount : %d";
    private static final String FORMAT_PERCENT = "voucher type : %s voucher Id : %s percent : %d";

    public void initApplication() {
        textTerminal.println("=== Voucher Application ===");
        textTerminal.println("Type create to create a new voucher");
        textTerminal.println("Type list to list all vouchers");
        textTerminal.println("Type update to update voucher");
        textTerminal.println("Type delete to delete voucher");
    }

    public void showWhenEntervoucherType() {
        textTerminal.println("\nType fixed to create a new fixed voucher.");
        textTerminal.println("Type percent to create a new percent voucher.");
    }

    public void showWhenEntervoucherNumber() {
        textTerminal.println("\nNumber of voucher's amount or percent.");
    }

    public List<String> changeVoucherToString(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(this::changeVoucherToString)
                .toList();
    }

    private String changeVoucherToString(Voucher voucher) {
        VoucherType voucherType = voucher.getVoucherType();
        UUID voucherId = voucher.getVoucherId();
        Long number = voucher.getAmount();
        if (voucherType == VoucherType.FIXED) return String.format(FORMAT_FIXED, voucherType, voucherId, number);
        return String.format(FORMAT_PERCENT, voucherType, voucherId, number);
    }

}
