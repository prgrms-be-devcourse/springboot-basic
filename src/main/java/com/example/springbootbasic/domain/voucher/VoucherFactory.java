package com.example.springbootbasic.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.BiFunction;

import static com.example.springbootbasic.domain.voucher.VoucherType.*;
import static com.example.springbootbasic.exception.voucher.VoucherExceptionMessage.NULL_VOUCHER_FACTORY_EXCEPTION;

public enum VoucherFactory {

    FIXED_AMOUNT_VOUCHER(FIXED_AMOUNT, FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER(PERCENT_DISCOUNT, PercentDiscountVoucher::new);

    private static final Logger logger = LoggerFactory.getLogger(VoucherFactory.class);
    private static final int UNKNOWN_VOUCHER_ID = 0;
    private final VoucherType voucherType;
    private final BiFunction<Long, Long, Voucher> voucherGenerator;

    VoucherFactory(VoucherType voucherType, BiFunction<Long, Long, Voucher> voucherGenerator) {
        this.voucherType = voucherType;
        this.voucherGenerator = voucherGenerator;
    }

    public static Voucher of(long discountValue, VoucherType inputVoucherType) {
        VoucherFactory findVoucherFactory = Arrays.stream(VoucherFactory.values())
                .filter(voucherFactory -> voucherFactory.voucherType == inputVoucherType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NULL_VOUCHER_FACTORY_EXCEPTION.getMessage()));
        logger.info("Success - discountValue = {}, inputVoucherType = {}", discountValue, inputVoucherType);
        return findVoucherFactory.generate(UNKNOWN_VOUCHER_ID, discountValue);
    }

    public static Voucher of(long voucherId, long discountValue, VoucherType inputVoucherType) {
        VoucherFactory findVoucherFactory = Arrays.stream(VoucherFactory.values())
                .filter(voucherFactory -> voucherFactory.voucherType == inputVoucherType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NULL_VOUCHER_FACTORY_EXCEPTION.getMessage()));

        return findVoucherFactory.generate(voucherId, discountValue);
    }

    private synchronized Voucher generate(long voucherId, long discountValue) {
        return voucherGenerator.apply(voucherId, discountValue);
    }
}
