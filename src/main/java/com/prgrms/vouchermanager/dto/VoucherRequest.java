package com.prgrms.vouchermanager.dto;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import lombok.Builder;
import lombok.Getter;

public class VoucherRequest {
    public record VoucherCreateRequest(String voucherType, int discount) {
        @Builder
        public VoucherCreateRequest {
        }
    }

    public record VoucherFindByConditionRequest(String voucherType,
                                                int startYear,
                                                int startMonth,
                                                int endYear,
                                                int endMonth) {
        @Builder
        public VoucherFindByConditionRequest {
        }
    }

    public static VoucherCreateRequest toCreateVoucher(VoucherType voucherType, int discount) {
        return VoucherCreateRequest.builder()
                .voucherType(voucherType.getLabel())
                .discount(discount)
                .build();
    }
}
