package kdt.vouchermanagement.domain.voucher.domain;

public enum VoucherType {
    FIXED_AMOUNT("1") {
        @Override
        public Voucher create(String discountValue) {
            return null;
        }
    };

    private String voucherTypeNum;

    VoucherType(String voucherTypeNum) {
        this.voucherTypeNum = voucherTypeNum;
    }

    public static VoucherType from(String inputNum) {
        return null;
    }

    public abstract Voucher create(String discountValue);
}
