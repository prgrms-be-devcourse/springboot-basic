package com.dojinyou.devcourse.voucherapplication.voucher.Entity;


public class Voucher {
    public static class Request {
        private final VoucherType voucherType;
        private final VoucherAmount voucherAmount;
        public Request(VoucherType voucherType, VoucherAmount voucherAmount) {
            this.voucherType = voucherType;
            this.voucherAmount = voucherAmount;
        }
    }
}
