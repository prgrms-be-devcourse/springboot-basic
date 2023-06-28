package org.prgms.voucher.voucher;

import org.prgms.voucher.type.OptionType;

public class VoucherCreateDto {
    private final OptionType optionType;
    private final int amount;

    public VoucherCreateDto(OptionType optionType, int amount) {
        this.optionType = optionType;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public OptionType getOptionType() {
        return optionType;
    }
}
