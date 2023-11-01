package com.prgrms.vouchermanager.dto.customer;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;


public class CustomerResponse {
    public record CustomerDetailResponse(UUID customerId, String name, int yearOfBirth, boolean isBlacklist) {
        @Builder
        public CustomerDetailResponse {
        }
    }
}
