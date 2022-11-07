package org.prgrms.springorder.service;

import java.util.UUID;
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;

public class VoucherFactory {

    private VoucherFactory() {
    }

    public static Voucher create(VoucherCreateRequest request) {

        VoucherType voucherType = request.getVoucherType();

        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(UUID.randomUUID(), request.getDiscountAmount());
            case PERCENT:
                long discountAmount = request.getDiscountAmount();
                if (discountAmount > 100) {
                    throw new IllegalArgumentException("할인율은 100%를 넘을 수 없습니다.");
                }
                return new PercentDiscountVoucher(UUID.randomUUID(), request.getDiscountAmount());

            default:
                throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }
}
