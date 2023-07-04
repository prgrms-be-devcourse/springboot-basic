package org.prgrms.vouchermission;

import java.time.LocalDate;

public enum VoucherFactory {

    PERCENT {
        private final static long TEMP_VOUCHER_ID = Long.MIN_VALUE;

        @Override
        public Voucher createVoucher(long voucherId, VoucherVO voucherVO) {
            return new PercentDiscountVoucher(voucherId, voucherVO.getValue(), voucherVO.getCreatedDate(), voucherVO.getExpirationDate());
        }

        @Override
        public Voucher createTempVoucher(VoucherVO voucherVO) {
            return new PercentDiscountVoucher(TEMP_VOUCHER_ID, voucherVO.getValue(), voucherVO.getCreatedDate(), voucherVO.getExpirationDate());
        }

    },
    AMOUNT {
        private final static long TEMP_VOUCHER_ID = Long.MIN_VALUE;

        @Override
        public Voucher createVoucher(long voucherId, VoucherVO voucherVO) {
            return new FixedAmountVoucher(voucherId, voucherVO.getValue(), voucherVO.getCreatedDate(), voucherVO.getExpirationDate());
        }

        @Override
        public Voucher createTempVoucher(VoucherVO voucherVO) {
            return new PercentDiscountVoucher(TEMP_VOUCHER_ID, voucherVO.getValue(), voucherVO.getCreatedDate(), voucherVO.getExpirationDate());
        }
    };

    public abstract Voucher createVoucher(long voucherId, VoucherVO voucherVO);
    public abstract Voucher createTempVoucher(VoucherVO voucherVO);
}

