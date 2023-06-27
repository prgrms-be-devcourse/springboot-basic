package org.devcourse.voucher.console.dto;

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
        private final String voucherType;
        private final int discountAmount;

        public VoucherSaveRequest(String voucherType, int discountAmount) {
            this.voucherType = voucherType;
            this.discountAmount = discountAmount;
        }
    }
}
