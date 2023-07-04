package org.prgms.voucher.voucher;

public class AmountVoucherCreateDto {

    private final AmountVoucherOptionType amountVoucherOptionType;
    private final int amount;

    public AmountVoucherCreateDto(AmountVoucherOptionType amountVoucherOptionType, int amount) {
        this.amountVoucherOptionType = amountVoucherOptionType;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public AmountVoucher getCreateAmountVoucher() {
        return amountVoucherOptionType.createAmountVoucher(amount);
    }
}
