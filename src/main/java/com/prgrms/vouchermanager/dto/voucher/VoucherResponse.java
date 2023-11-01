package com.prgrms.vouchermanager.dto.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;


public class VoucherResponse {
    public record VoucherDetailResponse(UUID voucherId, VoucherType voucherType, int discount) {
        @Builder
        public VoucherDetailResponse {
        }
    }

    public record VoucherListResponse(List<Voucher> vouchers) {
        @Builder
        public VoucherListResponse {
        }
    }

    public record VoucherDeleteResponse(int count) {
        @Builder
        public VoucherDeleteResponse {
        }
    }

    public static VoucherDetailResponse toDetailVoucher(Voucher voucher) {
        return VoucherDetailResponse.builder()
                .voucherId(voucher.getId())
                .voucherType(voucher.getType())
                .discount(voucher.getDiscount())
                .build();
    }
}
