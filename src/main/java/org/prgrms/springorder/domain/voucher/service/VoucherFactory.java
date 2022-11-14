package org.prgrms.springorder.domain.voucher.service;

import java.util.UUID;
import org.prgrms.springorder.domain.voucher.api.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
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
