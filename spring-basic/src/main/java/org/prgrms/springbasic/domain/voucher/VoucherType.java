package org.prgrms.springbasic.domain.voucher;

import static java.util.UUID.randomUUID;
import static org.prgrms.springbasic.domain.voucher.Voucher.fixedVoucher;
import static org.prgrms.springbasic.domain.voucher.Voucher.percentVoucher;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.DISCOUNT_INFO_ERR;

public enum VoucherType {

    FIXED {
        @Override
        long discount(long beforeDiscount, long discountInfo) {
            if(discountInfo > beforeDiscount || discountInfo < 0) {
                throw new RuntimeException(DISCOUNT_INFO_ERR.getMessage());
            }

            return beforeDiscount - discountInfo;
        }

        @Override
        public Voucher createVoucher(long discountInfo) {
            return fixedVoucher(randomUUID(), discountInfo);
        }
    },
    PERCENT {
        @Override
        long discount(long beforeDiscount, long discountInfo) {
            if(discountInfo > 100 || discountInfo < 0) {
                throw new RuntimeException(DISCOUNT_INFO_ERR.getMessage());
            }

            return (long) (beforeDiscount - (beforeDiscount * (discountInfo / 100f)));
        }

        @Override
        public Voucher createVoucher(long discountInfo) {
            return percentVoucher(randomUUID(), discountInfo);
        }
    };

    abstract long discount(long beforeDiscount, long discountInfo);

    public abstract Voucher createVoucher(long discountInfo);
}
