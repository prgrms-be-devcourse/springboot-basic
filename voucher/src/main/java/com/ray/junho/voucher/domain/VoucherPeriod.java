package com.ray.junho.voucher.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class VoucherPeriod {
    private final LocalDateTime createdAt;
    private final LocalDateTime expireAt;

    public VoucherPeriod(LocalDateTime createdAt, LocalDateTime expireAt) {
        if (expireAt.isBefore(createdAt) || expireAt.isEqual(createdAt)) {
            throw new IllegalArgumentException("쿠폰 만료일은 생성일 이후여야 합니다.");
        }
        this.createdAt = createdAt;
        this.expireAt = expireAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherPeriod that = (VoucherPeriod) o;
        return Objects.equals(createdAt, that.createdAt) && Objects.equals(expireAt, that.expireAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, expireAt);
    }
}
