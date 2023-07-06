package org.prgms.voucher.voucher;

public class AmountVoucherCreateDto {
    private final AmountVoucherOptionType amountVoucherOptionType;
    private final int initialMoney;
    private final int amount;

    public AmountVoucherCreateDto(AmountVoucherOptionType amountVoucherOptionType, int initialMoney, int amount) {
        this.amountVoucherOptionType = amountVoucherOptionType;
        this.initialMoney = initialMoney;
        this.amount = amount;
    }

    public int getInitialMoney() {
        return initialMoney;
    }

    public int getAmount() {
        return amount;
    }

    public AmountVoucherOptionType getAmountVoucherOptionType() {
        return amountVoucherOptionType;
    }
}
