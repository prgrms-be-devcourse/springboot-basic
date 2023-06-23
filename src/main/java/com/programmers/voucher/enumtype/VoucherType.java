package com.programmers.voucher.enumtype;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public enum VoucherType {
    FIXED_AMOUNT("fixed",
            FixedAmountVoucher::new,
            (amount) -> amount > 0,
            "Fixed amount must be positive."),
    PERCENT("percent",
            PercentDiscountVoucher::new,
            (amount) -> amount >= 0 && amount <= 100,
            "Percent discount must between 0 and 100.");

    private static final Logger log = LoggerFactory.getLogger(VoucherType.class);

    private final String type;
    private final BiFunction<UUID, VoucherCreateRequest, Voucher> createInstance;
    private final Predicate<Integer> discountRange;
    private final String validAmountMessage;

    VoucherType(String value,
                BiFunction<UUID, VoucherCreateRequest, Voucher> createInstance,
                Predicate<Integer> discountRange,
                String validAmountMessage) {
        this.type = value;
        this.createInstance = createInstance;
        this.discountRange = discountRange;
        this.validAmountMessage = validAmountMessage;
    }

    public static VoucherType getValue(String voucherType) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, voucherType))
                .findAny()
                .orElseThrow(() -> {
                    StringBuilder sb = new StringBuilder("Voucher type is invalid.")
                            .append(" Current input: ")
                            .append(voucherType);

                    log.warn("Invalid voucher type: {}", sb);
                    return new IllegalArgumentException(sb.toString());
                });
    }

    public Voucher createVoucher(UUID voucherId, VoucherCreateRequest request) {
        return createInstance.apply(voucherId, request);
    }

    public void validateAmount(Integer amount) {
        boolean amountInRange = discountRange.test(amount);
        if (!amountInRange) {
            StringBuilder sb = new StringBuilder(validAmountMessage)
                    .append(" Current input: ")
                    .append(amount);

            log.warn("Invalid voucher amount: {}", sb);
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
