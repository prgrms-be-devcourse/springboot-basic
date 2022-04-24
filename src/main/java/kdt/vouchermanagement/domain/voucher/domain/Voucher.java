package kdt.vouchermanagement.domain.voucher.domain;

public abstract class Voucher {
    private final long voucherId;
    private final VoucherType voucherType;
    private final int discountValue;

    public Voucher(VoucherType voucherType, int discountValue) {
        this.voucherId = 0;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public Voucher(long voucherId, VoucherType voucherType, int discountValue) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}