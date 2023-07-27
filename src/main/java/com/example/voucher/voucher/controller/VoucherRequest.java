package com.example.voucher.voucher.controller;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class VoucherRequest {

    private UUID voucherId;
    private VoucherType voucherType;
    private long discountValue;

    private VoucherRequest(UUID voucherId, VoucherType voucherType, long discountValue) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID voucherId;
        private VoucherType voucherType;
        private long discountValue;

        public Builder setVoucherId(UUID voucherId) {
            this.voucherId = voucherId;

            return this;
        }

        public Builder setVoucherType(VoucherType voucherType) {
            this.voucherType = voucherType;

            return this;
        }

        public Builder setDiscountValue(long discountValue) {
            this.discountValue = discountValue;

            return this;
        }

        public VoucherRequest build() {
            return new VoucherRequest(voucherId, voucherType, discountValue);
        }

    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}
