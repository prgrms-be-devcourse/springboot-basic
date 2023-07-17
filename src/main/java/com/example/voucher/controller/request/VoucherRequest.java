package com.example.voucher.controller.request;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class VoucherRequest {

    public static class Create {

        private VoucherType voucherType;
        private long discountValue;

        public Create(VoucherType voucherType, long discountValue) {
            this.voucherType = voucherType;
            this.discountValue = discountValue;
        }

        public VoucherType getVoucherType() {
            return voucherType;
        }

        public long getDiscountValue() {
            return discountValue;
        }

    }

    public static class Update {

        private UUID voucherId;
        private VoucherType voucherType;
        private long discountValue;

        public Update(UUID voucherId, VoucherType voucherType, long discountValue) {
            this.voucherId = voucherId;
            this.voucherType = voucherType;
            this.discountValue = discountValue;
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

}
