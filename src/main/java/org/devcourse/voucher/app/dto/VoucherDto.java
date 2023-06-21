package org.devcourse.voucher.app.dto;

import org.devcourse.voucher.domain.voucher.DiscountAmount;
import org.devcourse.voucher.domain.voucher.VoucherType;

import java.util.List;

public class VoucherDto {

    public static class VoucherResponse {
    }

    public static class VoucherListResponse {

        private final List<VoucherResponse> vouchers;

        public VoucherListResponse(List<VoucherResponse> vouchers) {
            this.vouchers = vouchers;
        }
    }

    public static class VoucherSaveResponse {
    }

    public static class VoucherSaveRequest {
        private final VoucherType voucherType;
        private final DiscountAmount amount;

        public VoucherSaveRequest(VoucherType voucherType, DiscountAmount amount) {
            this.voucherType = voucherType;
            this.amount = amount;
        }
    }
}
