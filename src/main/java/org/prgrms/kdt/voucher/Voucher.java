package org.prgrms.kdt.voucher;

public class Voucher {

    private VoucherType voucherType;
    private VoucherAmount amount;

    public Voucher(VoucherType voucherType, VoucherAmount amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public void validateAmount() {
        switch (this.voucherType) {
            case FIXED -> validateFixedAmount();
            case PERCENT -> validatePercentDiscount();
        }
    }

    private void validatePercentDiscount() {
        if (!amount.isValidFixedVoucherAmount()) {
            throw new IllegalStateException();
        }
    }

    private void validateFixedAmount() {
        if (amount.isValidPercentDiscountAmount()) {
            throw new IllegalStateException();
        }
    }
}
