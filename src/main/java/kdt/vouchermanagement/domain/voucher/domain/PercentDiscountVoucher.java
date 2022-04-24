package kdt.vouchermanagement.domain.voucher.domain;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(VoucherType voucherType, int discountValue) {
        super(voucherType, discountValue);
    }

    @Override
    public void validateValueRange() {

    }
}
