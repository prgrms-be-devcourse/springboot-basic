package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    /**
     * voucherType에 맞는 Voucher 객체를 생성하는 팩토리 메소드.
     */
    static Optional<Voucher> voucherFactory(VoucherType voucherType, long size) {
        Voucher newVoucher = switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), size);
            case PERSENT_DISCOUNT -> new PercentDiscountVoucher(UUID.randomUUID(), size);
            default -> null;
        };

        return Optional.of(newVoucher);
    }

    static Optional<Voucher> voucherFactory(VoucherType voucherType, long size, UUID voucherId) {
        Voucher newVoucher = switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, size);
            case PERSENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, size);
            default -> null;
        };

        return Optional.of(newVoucher);
    }
}
