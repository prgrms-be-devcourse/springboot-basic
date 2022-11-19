package org.prgrms.kdtspringdemo.domain.voucher.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MIN_AMOUNT = 0;
    private static final long MAX_AMOUNT = 1000000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) throws IllegalArgumentException {
        if (!voucherAllow(amount)) throw new IllegalArgumentException("허용되지 않는 숫자입니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return amount == that.amount && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, amount);
    }

    public CsvDto makeCsvDtoFromVoucher() {
        return CsvDto.from(this);
    }

    private boolean voucherAllow(long amount) {
        return amount >= MIN_AMOUNT && amount <= MAX_AMOUNT;
    }
}
