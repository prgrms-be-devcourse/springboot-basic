package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private static final long ZERO = 0;
    private static final String USED_VOUCHER = "사용한 바우처 입니다.";
    private static final String NOT_ENOUGH_MINIMUM_PRICE_CONDITION = "최소 요구 금액을 충족하지 못합니다. 최소 요구 금액: ";
    private static final String CURRENT_PAYMENT_AMOUNT = "현재 결제 금액: ";
    private static final String EXPIRED_VOUCHER = "만료된 바우처입니다. 만료일자: ";
    private static final String INVALID_ID = "빈 값이나 공백을 아이디로 사용할 수 없습니다. 현재 값: ";
    private static final String INVALID_NAME = "빈 값이나 공백을 이름으로 사용할 수 없습니다. 현재 값: ";
    private static final String INVALID_EXPIRATION_DATE = "생성일 이전을 만료일로 설정할 수 없습니다. 현재 값: ";

    private final UUID voucherId;
    private final String name;
    private final Long minimumPriceCondition;
    private final LocalDateTime createdDate;
    private final LocalDateTime expirationDate;

    private boolean used = false;

    protected Voucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate) {
        checkInvalidValue(voucherId, name, createdDate, expirationDate);
        this.voucherId = voucherId;
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition == null ? ZERO : minimumPriceCondition;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public Long getMinimumPriceCondition() {
        return minimumPriceCondition;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void use(Long priceBeforeDiscount, LocalDateTime at) {
        checkUndiscountable(priceBeforeDiscount, at);
        used = true;
    }

    private boolean isExpiration(LocalDateTime at) {
        return at.isAfter(expirationDate);
    }

    private boolean isUsed() {
        return used;
    }

    private boolean isInvalidVoucherId(UUID voucherId) {
        return voucherId == null;
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank();
    }

    private boolean isInvalidExpirationDate(LocalDateTime createdDate, LocalDateTime expirationDate) {
        return createdDate.isAfter(expirationDate);
    }

    private void checkUndiscountable(Long priceBeforeDiscount, LocalDateTime at) {
        if (isUsed()) {
            throw new IllegalStateException(USED_VOUCHER);
        }
        if (minimumPriceCondition > priceBeforeDiscount) {
            throw new IllegalArgumentException(NOT_ENOUGH_MINIMUM_PRICE_CONDITION + minimumPriceCondition + CURRENT_PAYMENT_AMOUNT + priceBeforeDiscount);
        }
        if (isExpiration(at)) {
            throw new IllegalArgumentException(EXPIRED_VOUCHER + expirationDate);
        }
    }

    private void checkInvalidValue(UUID voucherId, String name, LocalDateTime createdDate, LocalDateTime expirationDate) {
        if (isInvalidVoucherId(voucherId)) {
            throw new IllegalArgumentException(INVALID_ID + voucherId);
        }
        if (isInvalidName(name)) {
            throw new IllegalArgumentException(INVALID_NAME + name);
        }
        if (isInvalidExpirationDate(createdDate, expirationDate)) {
            throw new IllegalArgumentException(INVALID_EXPIRATION_DATE + expirationDate);
        }
    }

    protected abstract Long getDiscountPrice(Long priceBeforeDiscount);
}
