package org.prgrms.kdt.voucher;

public class Voucher {

    private VoucherType voucherType;
    private VoucherAmount amount;

    public Voucher(VoucherType voucherType, VoucherAmount amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public void validateAmount() {

    }
}
