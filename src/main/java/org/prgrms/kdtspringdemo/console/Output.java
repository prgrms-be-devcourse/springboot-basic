package org.prgrms.kdtspringdemo.console;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public String initMessage() {
        return """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """;
    }

    public String chooseVoucherTypeMessage() {
        return """
                === Choose Voucher ===
                1. FixedAmountVoucher(Fixed)
                2. PercentDiscountVoucher(Percent)
                Type Fixed if you want 1
                Type Percent if you want 2
                """;
    }

    public String FixedDiscountAmountMessage() {
        return """
                === Write Discount Amount ===
                Type over 0
                Type under 1,000,000
                """;
    }

    public String PercentDiscountAmountMessage() {
        return """
                === Write Percent Amount ===
                Type over 0
                Type under 100
                """;
    }
}
