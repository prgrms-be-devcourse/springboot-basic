package org.promgrammers.springbootbasic.service;

import org.promgrammers.springbootbasic.domain.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.Voucher;
import org.promgrammers.springbootbasic.domain.VoucherType;
import org.promgrammers.springbootbasic.dto.request.CreateVoucherRequest;

import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() throws InstantiationException {
        throw new InstantiationException("인스턴스화 할 수 없습니다.");
    }

    public static Voucher createVoucher(CreateVoucherRequest createVoucherRequest) {

        VoucherType voucherType = createVoucherRequest.voucherType();

        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(UUID.randomUUID(), createVoucherRequest.discountAmount());
            case PERCENT:
                return new PercentDiscountVoucher(UUID.randomUUID(), createVoucherRequest.discountAmount());
            default:
                throw new IllegalArgumentException("유효하지 않은 Voucher 요청입니다.");
        }
    }
}
