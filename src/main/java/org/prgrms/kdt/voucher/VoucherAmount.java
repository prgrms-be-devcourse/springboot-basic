package org.prgrms.kdt.voucher;

public class VoucherAmount {
    private Long value;

    public VoucherAmount(String amount) {
        this.value = Long.parseLong(amount);
    }

    public Long getValue() {
        return value;
    }
}
