package org.prgrms.prgrmsspring.domain;

import org.prgrms.prgrmsspring.entity.voucher.FixedAmountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.PercentDiscountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public enum VoucherType {
    FIXED_AMOUNT("FixedAmount", FixedAmountVoucher::new, FixedAmountVoucher.class::isInstance),
    PERCENT_DISCOUNT("PercentDiscount", PercentDiscountVoucher::new, PercentDiscountVoucher.class::isInstance);

    private final String title;
    private final BiFunction<UUID, Long, Voucher> constructVoucherFunc;
    private final Predicate<Voucher> checkInstanceOf;

    VoucherType(String title, BiFunction<UUID, Long, Voucher> constructVoucherFunc, Predicate<Voucher> checkInstanceOf) {
        this.title = title;
        this.constructVoucherFunc = constructVoucherFunc;
        this.checkInstanceOf = checkInstanceOf;
    }

    public String getTitle() {
        return title;
    }

    public static VoucherType of(String typeValue) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.title.equals(typeValue))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
    }

    public static VoucherType of(Voucher voucher) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.checkInstanceOf.test(voucher))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
    }

    public static VoucherType of(int modeNum) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.ordinal() + 1 == modeNum)
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
    }

    public Voucher constructVoucher(UUID uuid, Long value) {
        return this.constructVoucherFunc.apply(uuid, value);
    }


}
