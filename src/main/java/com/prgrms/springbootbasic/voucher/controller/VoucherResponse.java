package com.prgrms.springbootbasic.voucher.controller;

import com.prgrms.springbootbasic.voucher.domain.Voucher;

public class VoucherResponse {

    public static class VoucherShortcut{
        private String id;
        private String voucherType;

        private VoucherShortcut(String id, String voucherType) {
            this.id = id;
            this.voucherType = voucherType;
        }

        private VoucherShortcut() {
        }

        public static VoucherShortcut from(Voucher voucher) {
            return new VoucherShortcut(
                    voucher.getUUID().toString(),
                    voucher.getVoucherType().toString()
            );
        }

        public String getId() {
            return id;
        }

        public String getVoucherType() {
            return voucherType;
        }

        @Override
        public String toString() {
            return "VoucherShortcut{" +
                    "id='" + id + '\'' +
                    ", voucherType='" + voucherType + '\'' +
                    '}';
        }
    }

    public static class VoucherDetails{
        private String id;
        private int discountAmount;
        private String voucherType;

        private VoucherDetails(String id, int discountAmount, String voucherType) {
            this.id = id;
            this.discountAmount = discountAmount;
            this.voucherType = voucherType;
        }

        private VoucherDetails() {
        }

        public static VoucherDetails from(Voucher voucher) {
            return new VoucherDetails(
                    voucher.getUUID().toString(),
                    voucher.getDiscountAmount(),
                    voucher.getVoucherType().toString()
            );
        }

        public String getId() {
            return id;
        }

        public int getDiscountAmount() {
            return discountAmount;
        }

        public String getVoucherType() {
            return voucherType;
        }

        @Override
        public String toString() {
            return "VoucherDetails{" +
                    "id='" + id + '\'' +
                    ", discountAmount=" + discountAmount +
                    ", voucherType='" + voucherType + '\'' +
                    '}';
        }
    }
}
