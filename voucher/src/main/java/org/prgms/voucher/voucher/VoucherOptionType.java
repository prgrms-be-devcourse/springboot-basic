package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.factory.FixedAmountVoucherFactory;
import org.prgms.voucher.voucher.factory.PercentAmountVoucherFactory;
import org.prgms.voucher.voucher.factory.VoucherFactory;

public enum VoucherOptionType {
    FIXED_AMOUNT(
            "fixed amount",
            "fix",
            1
    ),
    PERCENT_AMOUNT(
            "percent amount",
            "percent",
            2
    );

    private final String typeName;
    private final String shortTypeName;
    private final int choiceNumber;
    
    VoucherOptionType(String optionType, String shortOptionType, int choiceNumber) {
        this.typeName = optionType;
        this.shortTypeName = shortOptionType;
        this.choiceNumber = choiceNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getShortTypeName() {
        return shortTypeName;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }
}
