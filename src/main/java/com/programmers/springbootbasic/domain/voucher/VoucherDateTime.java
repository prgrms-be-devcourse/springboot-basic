package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;

public class VoucherDateTime {
    private static final String INVALID_EXPIRED_AT = "생성일 이전을 만료일로 설정할 수 없습니다. 현재 값: ";
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public VoucherDateTime(LocalDateTime createdAt, LocalDateTime expiredAt) {
        if (isInvalidExpiredAt(createdAt, expiredAt)) {
            throw new IllegalArgumentException(INVALID_EXPIRED_AT + expiredAt);
        }
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public static VoucherDateTime of(LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new VoucherDateTime(createdAt, expiredAt);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public boolean isExpired(LocalDateTime at) {
        return at.isAfter(expiredAt);
    }

    private boolean isInvalidExpiredAt(LocalDateTime createdAt, LocalDateTime expiredAt) {
        return createdAt.isAfter(expiredAt);
    }
}
