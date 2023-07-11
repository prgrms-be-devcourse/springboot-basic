package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;

public class VoucherDateTime {
    private static final String INVALID_EXPIRED_AT = "생성일 이전을 만료일로 설정할 수 없습니다. 현재 값: ";
    private static final String CREATED_AT_IS_NULL = "빈 값이나 공백을 생성일로 지정할 수 없습니다. 현재 값: ";
    private static final String EXPIRED_AT_IS_NULL = "빈 값이나 공백을 만료일로 지정할 수 없습니다. 현재 값: ";
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public VoucherDateTime(LocalDateTime createdAt, LocalDateTime expiredAt) {
        checkNullValue(createdAt, expiredAt);
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

    private void checkNullValue(LocalDateTime createdAt, LocalDateTime expiredAt) {
        if (isNullCreatedAt(createdAt)) {
            throw new IllegalArgumentException(CREATED_AT_IS_NULL + createdAt);
        }
        if (isNullExpiredAt(expiredAt)) {
            throw new IllegalArgumentException(EXPIRED_AT_IS_NULL + expiredAt);
        }
    }

    private boolean isNullCreatedAt(LocalDateTime createdAt) {
        return createdAt == null;
    }

    private boolean isNullExpiredAt(LocalDateTime expiredAt) {
        return expiredAt == null;
    }

    private boolean isInvalidExpiredAt(LocalDateTime createdAt, LocalDateTime expiredAt) {
        return createdAt.isAfter(expiredAt);
    }
}
