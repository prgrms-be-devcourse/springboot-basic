package org.prgrms.vouchermission;

import java.time.LocalDate;

public enum VoucherFactory {

    PERCENT {
        @Override
        public Voucher createVoucher(VoucherVO voucherVO) {
            return new PercentDiscountVoucher(voucherVO.getValue(), voucherVO.getCreatedDate(), voucherVO.getExpirationDate());
        }

    },
    AMOUNT {
        @Override
        public Voucher createVoucher(VoucherVO voucherVO) {
            return new FixedAmountVoucher(voucherVO.getValue(), voucherVO.getCreatedDate(), voucherVO.getExpirationDate());
        }
    };

    public abstract Voucher createVoucher(VoucherVO voucherVO);
}

