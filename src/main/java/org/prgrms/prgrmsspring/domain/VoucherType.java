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
    FIXED_AMOUNT(1, "FixedAmount", FixedAmountVoucher::new, FixedAmountVoucher.class::isInstance),
    PERCENT_DISCOUNT(2, "PercentDiscount", PercentDiscountVoucher::new, PercentDiscountVoucher.class::isInstance);

    private final int modeNumber;
    private final String title;
    private final BiFunction<UUID, Long, Voucher> constructVoucherFunc;
    private final Predicate<Voucher> checkInstanceOf;

    VoucherType(int modeNumber, String title, BiFunction<UUID, Long, Voucher> constructVoucherFunc, Predicate<Voucher> checkInstanceOf) {
        this.modeNumber = modeNumber;
        this.title = title;
        this.constructVoucherFunc = constructVoucherFunc;
        this.checkInstanceOf = checkInstanceOf;
    }

    public String getTitle() {
        return title;
    }

    public static VoucherType from(String typeValue) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.title.equals(typeValue))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
    }

    public static VoucherType from(Voucher voucher) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.checkInstanceOf.test(voucher))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
    }

    public static VoucherType from(int inputModeNum) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.modeNumber == inputModeNum)
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
    }

    public Voucher constructVoucher(UUID uuid, Long value) {
        return this.constructVoucherFunc.apply(uuid, value);
    }


}
