package org.devcourse.voucher.stub;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.controller.dto.VoucherResponse;
import org.devcourse.voucher.application.voucher.model.FixedAmountVoucher;
import org.devcourse.voucher.application.voucher.model.PercentDiscountVoucher;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;

import java.util.List;
import java.util.UUID;

import static org.devcourse.voucher.application.voucher.model.VoucherType.FIXED_AMOUNT_VOUCHER;
import static org.devcourse.voucher.application.voucher.model.VoucherType.PERCENT_DISCOUNT_VOUCHER;

public class VoucherStubs {
    public static List<Voucher> voucherList() {
        return List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new FixedAmountVoucher(UUID.randomUUID(), 2000),
                new PercentDiscountVoucher(UUID.randomUUID(), 30),
                new PercentDiscountVoucher(UUID.randomUUID(), 60),
                new FixedAmountVoucher(UUID.randomUUID(), 10000)
        );
    }

    public static Voucher fixedAmountVoucher(UUID voucherId) {
        return new FixedAmountVoucher(voucherId, 10000);
    }

    public static VoucherRequest voucherRequest() {
        return new VoucherRequest(FIXED_AMOUNT_VOUCHER, 1000);
    }

    public static VoucherResponse voucherResponse(UUID voucherId) {
        return new VoucherResponse(voucherId, FIXED_AMOUNT_VOUCHER, 1000);
    }

    public static List<VoucherResponse> voucherReseponseList() {
        return List.of(
            new VoucherResponse(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 1000),
            new VoucherResponse(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 20000),
            new VoucherResponse(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 3000),
            new VoucherResponse(UUID.randomUUID(), PERCENT_DISCOUNT_VOUCHER, 30),
            new VoucherResponse(UUID.randomUUID(), PERCENT_DISCOUNT_VOUCHER, 99)
        );
    }
}
