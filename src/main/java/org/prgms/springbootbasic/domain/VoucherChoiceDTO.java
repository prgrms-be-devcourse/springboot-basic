package org.prgms.springbootbasic.domain;

public class VoucherChoiceDTO {
    private final VoucherType voucherType;
    private final long amount;

    public VoucherChoiceDTO(VoucherType voucherType, long amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }


    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "VoucherChoiceDTO{" +
                "voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
