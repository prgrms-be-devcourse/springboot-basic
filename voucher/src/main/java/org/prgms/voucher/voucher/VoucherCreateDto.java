package org.prgms.voucher.voucher;

public class VoucherCreateDto {

    private final VoucherOptionType voucherOptionType;
    private final int amount;

    public VoucherCreateDto(VoucherOptionType voucherOptionType, int amount) {
        this.voucherOptionType = voucherOptionType;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public VoucherOptionType getOptionType() {
        return voucherOptionType;
    }
}
