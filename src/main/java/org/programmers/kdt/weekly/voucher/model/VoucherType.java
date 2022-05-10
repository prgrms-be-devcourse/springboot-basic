package org.programmers.kdt.weekly.voucher.model;

import java.util.Arrays;
import java.util.function.Function;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {

    FIXED_AMOUNT_VOUCHER(1,
        voucherDto -> new FixedAmountVoucher(voucherDto.getVoucherId(), voucherDto.getValue(),
            voucherDto.getCreatedAt())),
    PERCENT_DISCOUNT_VOUCHER(2,
        (voucherDto) -> new PercentDiscountVoucher(voucherDto.getVoucherId(), voucherDto.getValue(),
            voucherDto.getCreatedAt()));

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    private final int number;
    private final Function<VoucherDto, Voucher> createVoucher;

    VoucherType(int number,
        Function<VoucherDto, Voucher> createVoucher) {
        this.number = number;
        this.createVoucher = createVoucher;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
            .filter(voucher -> voucher.number == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid number."));
    }

    public Voucher create(VoucherDto voucherDto) {
        return createVoucher.apply(voucherDto);
    }
}