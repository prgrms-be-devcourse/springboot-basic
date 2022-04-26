package org.prgrms.springbasic.domain.voucher;

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
    }, PERCENT {
        @Override
        long discount(long beforeDiscount, long discountInfo) {
            if(discountInfo > 100 || discountInfo < 0) {
                throw new RuntimeException(DISCOUNT_INFO_ERR.getMessage());
            }

            return (long) (beforeDiscount - (beforeDiscount * (discountInfo / 100f)));
        }
    };

    abstract long discount(long beforeDiscount, long discountInfo);
}
