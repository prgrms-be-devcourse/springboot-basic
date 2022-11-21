package org.prgms.springbootbasic.domain.voucher;

public record VoucherCreateDTO(VoucherType voucherType, long amount) {

    @Override
    public String toString() {
        return "VoucherChoiceDTO{" +
                "voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
