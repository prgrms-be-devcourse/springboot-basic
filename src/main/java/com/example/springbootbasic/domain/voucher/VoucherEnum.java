package com.example.springbootbasic.domain.voucher;

import com.example.springbootbasic.VoucherConsoleApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

public enum VoucherEnum {
    FIXED_AMOUNT_VOUCHER("fixed", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("percent", PercentDiscountVoucher::new);

    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleApplication.class);
    private final String voucherType;
    private final BiFunction<Long, Long, Voucher> voucherGenerator;

    VoucherEnum(String voucherType, BiFunction<Long, Long, Voucher> voucherGenerator) {
        this.voucherType = voucherType;
        this.voucherGenerator = voucherGenerator;
    }

    public static Voucher generateVoucher(Long voucherId, Long discountValue, VoucherEnum findVoucher) {
        logger.debug("[GENERATE] voucherId => '{}', discountValue => '{}', findVoucher => '{}'",
                voucherId, discountValue, findVoucher);
        return findVoucher.voucherGenerator.apply(voucherId, discountValue);
    }

    public static Optional<VoucherEnum> findVoucherBy(String findVoucherType) {
        return Arrays.stream(VoucherEnum.values())
                .filter(voucherGenerator -> voucherGenerator.voucherType.equals(findVoucherType))
                .findFirst();
    }

    public String getVoucherType() {
        return voucherType;
    }
}
