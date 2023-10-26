package team.marco.vouchermanagementsystem.model;

public enum VoucherType {
    FIXED, PERCENT;

    public static Voucher convertVoucher(LoadedVoucher loadedVoucher) {
        return switch (loadedVoucher.getType()) {
            case FIXED -> new FixedAmountVoucher(loadedVoucher.getId(), loadedVoucher.getAmount());
            case PERCENT -> new PercentDiscountVoucher(loadedVoucher.getId(), loadedVoucher.getPercent());
        };
    }
}
