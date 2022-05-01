package org.prgrms.voucherapp.global.enums;

import org.prgrms.voucherapp.engine.voucher.entity.FixedAmountVoucher;
import org.prgrms.voucherapp.engine.voucher.entity.PercentDiscountVoucher;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.global.Util;
import org.prgrms.voucherapp.global.custom.TriFunction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;


public enum VoucherType {

    FIX(FixedAmountVoucher::new, 10000),
    PERCENT(PercentDiscountVoucher::new, 30);

    private final TriFunction<UUID, Long, LocalDateTime, Voucher> createInstance;
    private final long maxDiscountAmount;

    VoucherType(TriFunction<UUID, Long, LocalDateTime, Voucher> createInstance, long maxDiscountAmount) {
        this.createInstance = createInstance;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public Voucher createVoucher(UUID uuid, long amount) {
        return this.createInstance.apply(uuid, amount, Util.NOW());
    }
    public Voucher createVoucher(UUID uuid, long amount, LocalDateTime createdAt) {
        return this.createInstance.apply(uuid, amount, createdAt);
    }

    public static Optional<VoucherType> getType(int option) {
        return Arrays.stream(values())
                .filter(o -> o.ordinal() == option-1)
                .findFirst();
    }

    public static Optional<VoucherType> getType(String typeName) {
        try{
            return Optional.of(VoucherType.valueOf(typeName.toUpperCase()));
        } catch(IllegalArgumentException e){
            return Optional.empty();
        }
    }

    public long getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
}
