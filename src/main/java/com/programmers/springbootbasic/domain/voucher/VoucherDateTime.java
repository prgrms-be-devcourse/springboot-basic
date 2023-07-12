package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.common.util.Validator;

import java.time.LocalDateTime;

public class VoucherDateTime {
    private static final String INVALID_EXPIRED_AT = "생성일 이전을 만료일로 설정할 수 없습니다. 현재 값: ";
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public VoucherDateTime(LocalDateTime createdAt, LocalDateTime expiredAt) {
        checkNullValue(createdAt, expiredAt);
        if (isInvalidExpiredAt(createdAt, expiredAt)) {
            throw new IllegalArgumentException(INVALID_EXPIRED_AT + expiredAt);
        }  // 이 부분을 Validator 로 빼려다가 범용성이 떨어지는 것 같아서 그냥 뒀는데, Validator 로 빼주는게 나을까요?
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
        Validator.checkInvalidDateTime(createdAt);
        Validator.checkInvalidDateTime(expiredAt);
    }

    private boolean isInvalidExpiredAt(LocalDateTime createdAt, LocalDateTime expiredAt) {
        return createdAt.isAfter(expiredAt);
    }
}
