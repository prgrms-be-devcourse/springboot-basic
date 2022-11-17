package com.programmers.VoucherManagementApplication.dto;

import com.programmers.VoucherManagementApplication.voucher.FixedVoucher;
import com.programmers.VoucherManagementApplication.voucher.PercentVoucher;
import com.programmers.VoucherManagementApplication.voucher.Voucher;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {

//    FIXED_AMOUNT("1", "\nA fixed discount", (originPrice, amount) -> originPrice - amount),
//    DISCOUNT_PERCENT("2", "\nA percent discount", (originPrice, amount) -> originPrice - (originPrice * amount) / 100);

    FIXED_DISCOUNT("fixed", FixedVoucher::new),
    PERCENT_DISCOUNT("percent", PercentVoucher::new);

    private final String inputValue;
    private final VoucherFunction<UUID, VoucherType, Amount, Voucher> voucherFunction;

//    private final String inputMessage;
//    private final BiFunction<Long, Long, Long> arithmetic;

    //    VoucherType(String inputValue, String inputMessage, BiFunction<Long, Long, Long> arithmetic) {
//        this.inputValue = inputValue;
//        this.inputMessage = inputMessage;
//        this.arithmetic = arithmetic;
//    }
    VoucherType(String inputValue, VoucherFunction<UUID, VoucherType, Amount, Voucher> voucherFunction) {
        this.inputValue = inputValue;
        this.voucherFunction = voucherFunction;
    }

    public VoucherFunction<UUID, VoucherType, Amount, Voucher> getVoucherFunction() {
        return voucherFunction;
    }

//    public String getInputMessage() {
//        return inputMessage;
//    }

//    public BiFunction<Long, Long, Long> getArithmetic() {
//        return this.arithmetic;
//    }

    public static VoucherType of(String inputValue) {
        return Arrays.stream(values())
                .filter(discountType -> discountType.inputValue.equals(inputValue))
                .findAny()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("\nThe input value is not valid. Please try again.\n");
                });
    }

    public Voucher createVoucher(VoucherType voucherType, Amount amount) {
        return voucherFunction
                .apply(UUID.randomUUID(), voucherType, amount);
    }
}
