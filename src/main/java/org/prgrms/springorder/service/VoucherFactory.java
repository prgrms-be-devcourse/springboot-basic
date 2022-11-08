package org.prgrms.springorder.service;

import java.util.UUID;
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherFactory {

    private static final Logger logger = LoggerFactory.getLogger(VoucherFactory.class);

    private VoucherFactory() {
    }

    public static Voucher create(VoucherCreateRequest request) {
        VoucherType voucherType = request.getVoucherType();

        return toVoucher(voucherType, UUID.randomUUID(), request.getDiscountAmount());
    }

    public static Voucher toVoucher(VoucherType voucherType, UUID voucherId, long amount) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherId, amount);

            case PERCENT:
                return new PercentDiscountVoucher(voucherId, amount);

            default:
                logger.error("invalid Voucher Type : {}", voucherType);
                throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }

}
