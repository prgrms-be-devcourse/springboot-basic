package org.prgrms.springbootbasic.dto;

import org.prgrms.springbootbasic.controller.VoucherType;

public class CreateVoucherRequest {

    private VoucherType voucherType;
    private int amount;
    private int percent;

    public CreateVoucherRequest() {
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getAmount() {
        return amount;
    }

    public int getPercent() {
        return percent;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "CreateVoucherRequest{" +
            "voucherType=" + voucherType +
            ", amount=" + amount +
            ", percent=" + percent +
            '}';
    }
}
