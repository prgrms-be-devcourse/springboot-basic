package kr.co.programmers.school.voucher.domain.voucher.dto;

import kr.co.programmers.school.voucher.domain.voucher.enums.VoucherType;

public class VoucherRequest {
    private final int amount;
    private final VoucherType voucherType;

    public VoucherRequest(int amount, VoucherType voucherType) {
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    public int getAmount() {
        return this.amount;
    }

    public static VoucherRequest of(int amount, VoucherType voucherType) {
        return new VoucherRequest(amount, voucherType);
    }
}