package org.prgms.voucher.voucher;

public class AmountVoucherCreateVo {
    private final AmountVoucherOptionType amountVoucherOptionType;
    private final int amount;

    public AmountVoucherCreateVo(AmountVoucherOptionType amountVoucherOptionType, int amount) {
        this.amountVoucherOptionType = amountVoucherOptionType;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public AmountVoucherOptionType getAmountVoucherOptionType() {
        return amountVoucherOptionType;
    }
}
