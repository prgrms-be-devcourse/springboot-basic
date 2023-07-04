package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;

public class VoucherDate {
    private static final String INVALID_EXPIRATION_DATE = "생성일 이전을 만료일로 설정할 수 없습니다. 현재 값: ";
    private final LocalDateTime createdDate;
    private final LocalDateTime expirationDate;

    public VoucherDate(LocalDateTime createdDate, LocalDateTime expirationDate) {
        if (isInvalidExpirationDate(createdDate, expirationDate)) {
            throw new IllegalArgumentException(INVALID_EXPIRATION_DATE + expirationDate);
        }
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpiration(LocalDateTime at) {
        return at.isAfter(expirationDate);
    }

    private boolean isInvalidExpirationDate(LocalDateTime createdDate, LocalDateTime expirationDate) {
        return createdDate.isAfter(expirationDate);
    }
}
