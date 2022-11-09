package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.utils.VoucherValidator;

import java.util.Objects;

public class Voucher {

    private VoucherType voucherType;
    private VoucherAmount amount;

    public Voucher(VoucherType voucherType, VoucherAmount amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public void validate(VoucherValidator voucherValidator) {
        voucherValidator.validateAmount(voucherType, amount);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public VoucherAmount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voucher)) return false;
        Voucher voucher = (Voucher) o;
        return voucherType == voucher.voucherType && amount.equals(voucher.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherType, amount);
    }

    @Override
    public String toString() {
        return "[Type]: " + voucherType.getType() + ", [amount]: " + amount.toString();
    }
}
