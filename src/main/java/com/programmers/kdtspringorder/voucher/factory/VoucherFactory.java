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
        throw new RuntimeException("일치하는 voucherType이 없습니다.");
    }

    public static Voucher createVoucherFromDB(VoucherType voucherType, UUID voucherId, UUID customerId, long value, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        if (voucherType == VoucherType.FIXED) {
            return new FixedAmountVoucher(voucherId, customerId, value, voucherType, used, createdAt, expirationDate);
        } else if (voucherType == VoucherType.PERCENT) {
            return new PercentDiscountVoucher(voucherId, customerId, value, voucherType, used, createdAt, expirationDate);
        }
        throw new RuntimeException("일치하는 voucherType이 없습니다.");
    }
}

