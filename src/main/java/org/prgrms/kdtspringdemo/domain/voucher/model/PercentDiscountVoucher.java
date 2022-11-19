package org.prgrms.kdtspringdemo.domain.voucher.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_PERCENT = 100;
    private static final long MIN_PERCENT = 0;
    private final UUID voucherId;

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) throws IllegalArgumentException {
        if (!voucherAllow(percent)) {
            throw new IllegalArgumentException("허용되지 않는 숫자입니다.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public CsvDto makeCsvDtoFromVoucher() {
        return CsvDto.from(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return percent == that.percent && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, percent);
    }

    private boolean voucherAllow(Long value) {
        return value >= MIN_PERCENT && value <= MAX_PERCENT;
    }
}
