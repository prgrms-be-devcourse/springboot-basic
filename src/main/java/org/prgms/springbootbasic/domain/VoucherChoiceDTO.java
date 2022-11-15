package org.prgms.springbootbasic.domain;

public record VoucherChoiceDTO(VoucherType voucherType, long amount) {

    @Override
    public String toString() {
        return "VoucherChoiceDTO{" +
                "voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
