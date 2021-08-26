package com.programmers.kdtspringorder.voucher.factory;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherFactory {

    private final Logger logger = LoggerFactory.getLogger(VoucherFactory.class);

    public Voucher createVoucher(VoucherType voucherType) {
        if (voucherType == VoucherType.FIXED) {
            return createVoucher(voucherType, 2000L);
        } else if (voucherType == VoucherType.PERCENT) {
            return createVoucher(voucherType, 10L);
        }

        return null;
    }

    public Voucher createVoucher(VoucherType voucherType, long value) {
        if (voucherType == VoucherType.FIXED) {
            FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), value);
            logger.info("CREATE {} {}", fixedAmountVoucher.getClass().getSimpleName(), fixedAmountVoucher.getVoucherId());
            return fixedAmountVoucher;
        } else if (voucherType == VoucherType.PERCENT) {
            PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), value);
            logger.info("CREATE {} {} ", percentDiscountVoucher.getClass().getSimpleName(), percentDiscountVoucher.getVoucherId());
            return percentDiscountVoucher;
        }

        return null;
    }

    public static Voucher createVoucher(VoucherType voucherType, UUID voucherId, UUID customerId, long value, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        if (voucherType == VoucherType.FIXED) {
            FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, customerId, value, voucherType, used, createdAt, expirationDate);
            return fixedAmountVoucher;
        } else if (voucherType == VoucherType.PERCENT) {
            PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, customerId, value, voucherType, used, createdAt, expirationDate);
            return percentDiscountVoucher;
        }
        return null;
    }
}

