package kdt.vouchermanagement.domain.voucher.domain;

public enum VoucherFactory {

    public static Optional<VoucherFactory> from(VoucherType type) {
        return null;
    }

    public abstract Voucher create(int discountValue);
}
