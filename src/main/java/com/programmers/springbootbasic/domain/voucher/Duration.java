package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.common.util.Validator;

import java.time.LocalDateTime;

public class Duration {
    private static final String INVALID_EXPIRED_AT = "생성일 이전을 만료일로 설정할 수 없습니다. 현재 값: ";
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public Duration(LocalDateTime createdAt, LocalDateTime expiredAt) {
        checkNullValue(createdAt, expiredAt);
        if (isInvalidExpiredAt(createdAt, expiredAt)) {
            throw new IllegalArgumentException(INVALID_EXPIRED_AT + expiredAt);
        }
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public static Duration of(LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new Duration(createdAt, expiredAt);
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
        Validator.checkNullDateTime(createdAt);
        Validator.checkNullDateTime(expiredAt);
    }

    private boolean isInvalidExpiredAt(LocalDateTime createdAt, LocalDateTime expiredAt) {
        return createdAt.isAfter(expiredAt);
    }
}
