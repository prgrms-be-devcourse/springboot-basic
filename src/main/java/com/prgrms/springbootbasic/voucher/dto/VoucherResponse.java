package com.prgrms.springbootbasic.voucher.dto;

public class VoucherResponse {
    String voucherType;
    int amount;

    public VoucherResponse(String voucherType, int amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Voucher[" +
                "voucherType=" + voucherType +
                ", amount=" + amount +
                ']';
    }
}
