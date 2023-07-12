package com.programmers.voucher.domain;

import com.programmers.voucher.dto.VoucherRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private static final String VOUCHER_NULL_MESSAGE = "[ERROR] 유효하지 않은 Voucher Id 입니다. (null)";
    private static final String EXPIRED_VOUCHER_MESSAGE = "[ERROR] 기간이 만료된 Voucher 입니다.";
    private static int VOUCHER_EXPIRATION_POLICY = 7;

    private final UUID voucherId;
    private final Discount discount;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public Voucher(UUID voucherId, Discount discount, LocalDateTime createdAt) {
        validateVoucherId(voucherId);
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.expiredAt = createdAt.plusDays(VOUCHER_EXPIRATION_POLICY);
    }

    public Voucher(UUID voucherId, Discount discount, LocalDateTime createdAt, LocalDateTime expiration) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.expiredAt = expiration;
    }

    public static Voucher from(UUID voucherId, VoucherRequestDto requestDto) {
        Discount discount = Discount.of(requestDto.discountType(), requestDto.value());
        return new Voucher(voucherId, discount, LocalDateTime.now());
    }

    public long discountWith(long itemPrice, LocalDateTime usedAt) {
        checkVoucherExpiration(usedAt);
        return discount.applyDiscount(itemPrice);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Discount getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    private void validateVoucherId(UUID voucherId) {
        if (voucherId == null) throw new IllegalArgumentException(VOUCHER_NULL_MESSAGE);
    }

    private void checkVoucherExpiration(LocalDateTime usedAt) {
        if (usedAt.isAfter(expiredAt)) throw new IllegalArgumentException(EXPIRED_VOUCHER_MESSAGE);
    }
}
