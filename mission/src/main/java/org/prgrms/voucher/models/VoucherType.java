package org.prgrms.voucher.models;

public enum VoucherType {

    FIXED_AMOUNT {
        @Override
        public Voucher createVoucher(long discountValue, VoucherType voucherType) {

            return new FixedAmountVoucher(discountValue, voucherType);
        }

        @Override
        public Voucher createVoucher(Long voucherId, long discountValue, VoucherType voucherType) {

            return new FixedAmountVoucher(voucherId, discountValue, voucherType);
        }
    },
    PERCENT_DISCOUNT {
        @Override
        public Voucher createVoucher(long discountValue, VoucherType voucherType) {

            return null;
        }

        @Override
        public Voucher createVoucher(Long voucherId, long discountValue, VoucherType voucherType) {

            return null;
        }
    };


    public abstract Voucher createVoucher(long discountValue, VoucherType voucherType);

    public abstract Voucher createVoucher(Long voucherId, long discountValue, VoucherType voucherType);

    VoucherType() {
    }
}
