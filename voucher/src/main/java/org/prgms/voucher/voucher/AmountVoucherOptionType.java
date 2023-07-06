package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.factory.AmountVoucherFactory;
import org.prgms.voucher.voucher.factory.FixedAmountVoucherFactory;
import org.prgms.voucher.voucher.factory.PercentAmountVoucherFactory;

public enum AmountVoucherOptionType {
    FIXED_AMOUNT(
            "fixed amount",
            "fix",
            1,
            new FixedAmountVoucherFactory()
    ),
    PERCENT_AMOUNT(
            "percent amount",
            "percent",
            2,
            new PercentAmountVoucherFactory()
    );

    private final String typeName;
    private final String shortTypeName;
    private final int choiceNumber;
    private final AmountVoucherFactory amountVoucherFactory;

    AmountVoucherOptionType(String optionType, String shortOptionType, int choiceNumber, AmountVoucherFactory amountVoucherFactory) {
        this.typeName = optionType;
        this.shortTypeName = shortOptionType;
        this.choiceNumber = choiceNumber;
        this.amountVoucherFactory = amountVoucherFactory;
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

    public AmountVoucher createAmountVoucher(int initialMoney, int amount) {
        return amountVoucherFactory.createVoucher(initialMoney, amount);
    }
}
