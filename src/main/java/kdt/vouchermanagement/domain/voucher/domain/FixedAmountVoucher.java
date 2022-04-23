package kdt.vouchermanagement.domain.voucher.domain;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(VoucherType voucherType, int discountValue) {
        super(voucherType, discountValue);
    }
}
