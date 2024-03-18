package org.prgrms.vouchermanagement.dto;

import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;

import java.util.UUID;

public record VoucherResponseDto(
        UUID voucherId,
        DiscountPolicy discountPolicy
) { }
