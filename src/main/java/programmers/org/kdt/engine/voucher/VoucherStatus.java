package programmers.org.kdt.engine.voucher;

public enum VoucherStatus {
    Null,
    FixedAmountVoucher,
    PercentDiscountVoucher;

    public static VoucherStatus fromString(String string) {
        if (string.equals("FixedAmountVoucher")) {
            return FixedAmountVoucher;
        } else if (string.equals("PercentDiscountVoucher")){
            return PercentDiscountVoucher;
        } else {
            return Null;
        }
    }
}
