package com.prgrms.springbootbasic.voucher.controller;

public class VoucherForm {


    private int discountAmount;
    private String voucherType;

    public VoucherForm() {
    }

    public VoucherForm(int discountAmount, String voucherType) {
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    @Override
    public String toString() {
        return "VoucherForm{" +
                "discountAmount=" + discountAmount +
                ", voucherType='" + voucherType + '\'' +
                '}';
    }
}
