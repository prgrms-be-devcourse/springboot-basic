package org.prgrms.springorder.domain.voucher.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.Customer;
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

        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(UUID.randomUUID(), request.getDiscountAmount());

            case PERCENT:
                return new PercentDiscountVoucher(UUID.randomUUID(), request.getDiscountAmount());

            default:
                logger.error("invalid Voucher Type : {}", voucherType);
                throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }

    public static Voucher toVoucher(VoucherType voucherType, UUID voucherId, long amount, UUID customerId, LocalDateTime createdAt) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherId, amount, customerId, createdAt);

            case PERCENT:
                return new PercentDiscountVoucher(voucherId, amount, customerId, createdAt);

            default:
                logger.error("invalid Voucher Type : {}", voucherType);
                throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }

}
