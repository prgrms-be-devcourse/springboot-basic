package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final TypeOfVoucher typeOfVoucher;
    private long discount;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;
    private final String customerEmail;

    public Voucher(UUID voucherId, TypeOfVoucher typeOfVoucher, long discount, LocalDateTime createdAt, LocalDateTime expiredAt, String customerEmail) {
        this.voucherId = voucherId;
        this.typeOfVoucher = typeOfVoucher;
        this.discount = discount;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.customerEmail = customerEmail;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public TypeOfVoucher getTypeOfVoucher() {
        return typeOfVoucher;
    }

    public long getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", typeOfVoucher=" + typeOfVoucher +
                ", discount=" + discount +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}