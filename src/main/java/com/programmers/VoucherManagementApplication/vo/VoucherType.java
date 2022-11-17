package com.programmers.VoucherManagementApplication.vo;

import com.programmers.VoucherManagementApplication.io.Message;
import com.programmers.VoucherManagementApplication.voucher.FixedVoucher;
import com.programmers.VoucherManagementApplication.voucher.PercentVoucher;
import com.programmers.VoucherManagementApplication.voucher.Voucher;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {

    FIXED_DISCOUNT("fixed", FixedVoucher::new),
    PERCENT_DISCOUNT("percent", PercentVoucher::new);

    private final String inputValue;
    private final VoucherFunction<UUID, VoucherType, Amount, Voucher> voucherFunction;

    VoucherType(String inputValue, VoucherFunction<UUID, VoucherType, Amount, Voucher> voucherFunction) {
        this.inputValue = inputValue;
        this.voucherFunction = voucherFunction;
    }

    public VoucherFunction<UUID, VoucherType, Amount, Voucher> getVoucherFunction() {
        return voucherFunction;
    }

    public static VoucherType of(String inputValue) {
        return Arrays.stream(values())
                .filter(discountType -> discountType.inputValue.equals(inputValue))
                .findAny()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(Message.INVALID_INPUT.getMessage());
                });
    }

    public Voucher createVoucher(VoucherType voucherType, Amount amount) {
        return voucherFunction
                .apply(UUID.randomUUID(), voucherType, amount);
    }
}
