package com.programmers.vouchermanagement.dto;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    public static class Create {
        public UUID voucherId;
        public String voucherName;
        public float discountAmount;
        public LocalDateTime createdAt;
        public VoucherType voucherType;

        public Create(String voucherName, float discountAmount, VoucherType voucherType) {
            this.voucherName = voucherName;
            this.discountAmount = discountAmount;
            this.voucherType = voucherType;
        }

        public Create(UUID voucherId, String voucherName, float discountAmount, LocalDateTime createdAt, VoucherType voucherType) {
            this.voucherId = voucherId;
            this.voucherName = voucherName;
            this.discountAmount = discountAmount;
            this.createdAt = createdAt;
            this.voucherType = voucherType;
        }

        public Create(String[] voucherInfo) {
            this.voucherId = UUID.fromString(voucherInfo[0]);
            this.voucherName = voucherInfo[1];
            this.discountAmount = Float.parseFloat(voucherInfo[2]);
            this.createdAt = LocalDateTime.parse(voucherInfo[3]);
            this.voucherType = VoucherType.valueOf(voucherInfo[4]);
        }
    }
}
