package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.voucher.model.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {
    private static final Logger logger = LoggerFactory.getLogger(VoucherCreator.class);

    public Voucher createVoucher(VoucherType voucherType, Long percentOrAmount) throws IllegalArgumentException {

        Voucher newVoucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), percentOrAmount);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), percentOrAmount);
        };
        logger.info("바우쳐 생성 성공. 타입 :{} 값 :{}", voucherType.name(), percentOrAmount);
        return newVoucher;
    }

    public Voucher createVoucher(UUID voucherId, VoucherType voucherType, Long percentOrAmount) throws IllegalArgumentException {

        Voucher newVoucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, percentOrAmount);
            case PERCENT -> new PercentDiscountVoucher(voucherId, percentOrAmount);
        };
        logger.info("바우쳐 생성 성공. 타입 :{} 값 :{}", voucherType.name(), percentOrAmount);
        return newVoucher;
    }
}
