package kdt.vouchermanagement.domain.voucher.domain;

public abstract class Voucher {
    private long voucherId;
    private final VoucherType voucherType;
    private final int discountValue;

    public Voucher(VoucherType voucherType, int discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
