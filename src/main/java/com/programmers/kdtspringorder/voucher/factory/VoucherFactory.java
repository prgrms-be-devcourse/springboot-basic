package com.programmers.kdtspringorder.voucher.factory;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    private final Logger logger = LoggerFactory.getLogger(VoucherFactory.class);

    public Voucher createVoucher(VoucherType voucherType) {
        if (voucherType == VoucherType.FIXED) {
            FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 2000L);
            logger.info("CREATE " + fixedAmountVoucher.getClass().getSimpleName() + " " + fixedAmountVoucher.getVoucherId());
            return fixedAmountVoucher;
        } else {
            PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
            logger.info("CREATE " + percentDiscountVoucher.getClass().getSimpleName() + " " + percentDiscountVoucher.getVoucherId());
            return percentDiscountVoucher;
        }
    }
}

