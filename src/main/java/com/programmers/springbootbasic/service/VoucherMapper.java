package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Duration;
import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.service.dto.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.VoucherResponse;
import com.programmers.springbootbasic.service.dto.VoucherResponses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class VoucherMapper {
    private VoucherMapper() {

    }

    public static Voucher toVoucher(VoucherCreationRequest request) {
        Duration duration = Duration.of(LocalDateTime.now(), request.expirationDate());
        VoucherType voucherType = VoucherType.from(request.voucherType());
        return switch (voucherType) {
            case FIX ->
                    Voucher.createFixedAmount(UUID.randomUUID(), voucherType, request.name(), request.minimumPriceCondition(), duration, request.amountOrPercent());
            case PERCENT ->
                    Voucher.createPercentDiscount(UUID.randomUUID(), voucherType, request.name(), request.minimumPriceCondition(), duration, request.amountOrPercent());
        };
    }

    public static VoucherResponses toVoucherResponseList(List<Voucher> vouchers) {
        List<VoucherResponse> voucherResponses = vouchers.stream()
                .map(VoucherMapper::toVoucherResponse)
                .toList();
        return new VoucherResponses(voucherResponses);
    }

    public static VoucherResponse toVoucherResponse(Voucher voucher) {
        return new VoucherResponse(
                voucher.getName(),
                voucher.getMinimumPriceCondition(),
                voucher.getDuration()
        );
    }
}
