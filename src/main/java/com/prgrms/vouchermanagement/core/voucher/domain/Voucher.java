package com.prgrms.vouchermanagement.core.voucher.domain;

import java.util.Objects;
import java.util.UUID;

public class Voucher {

    private final String id;
    private final String name;
    private final long amount;
    private final VoucherType voucherType;

    public Voucher(String name, long amount, String voucherType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        validateVoucherType(voucherType);
        this.voucherType = VoucherType.getType(voucherType);
        validateAmountByVoucherType(this.voucherType, amount);
        this.amount = amount;
    }

    private void validateVoucherType(String voucherType) {
        if (VoucherType.getType(voucherType) == null) {
            throw new IllegalArgumentException("맞는 VoucherType 이 존재하지 않습니다.");
        }
    }

    private void validateAmountByVoucherType(VoucherType voucherType, long amount) {
        switch (voucherType) {
            case FIXED:
                validateAmountByFixed(amount);
                break;
            case RATE:
                validateAmountByRate(amount);
                break;
        }
    }

    private void validateAmountByFixed(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("고정 할인 금액은 0보다 작을 수 없습니다.");
        }
    }

    private void validateAmountByRate(long amount) {
        if (amount < 0 || amount > 100) {
            throw new IllegalArgumentException("할인율은 0과 100 사이이어야 합니다.");
        }
    }

    public Voucher(String id, String name, long amount, VoucherType voucherType) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return amount == voucher.amount && Objects.equals(name, voucher.name) && voucherType == voucher.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, voucherType);
    }
}
