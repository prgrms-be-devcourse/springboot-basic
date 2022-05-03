package org.prgrms.springbootbasic.dto;

import org.prgrms.springbootbasic.controller.VoucherType;

public class CreateVoucherRequest {

    private VoucherType voucherType;
    private Integer amount;
    private Integer percent;

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
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
