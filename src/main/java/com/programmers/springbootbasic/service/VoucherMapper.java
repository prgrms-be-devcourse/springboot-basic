package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherDateTime;
import com.programmers.springbootbasic.service.dto.FixedAmountVoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.PercentDiscountVoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.VoucherResponse;
import com.programmers.springbootbasic.service.dto.VoucherResponses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class VoucherMapper {
    private VoucherMapper() {

    }

    public static Voucher toFixedAmountVoucher(FixedAmountVoucherCreationRequest request) {
        VoucherDateTime voucherDateTime = VoucherDateTime.of(LocalDateTime.now(), request.expirationDate());
        return Voucher.createFixedAmount(UUID.randomUUID(), request.name(), request.minimumPriceCondition(), voucherDateTime, request.amount());
    }

    public static Voucher toPercentDiscountVoucher(PercentDiscountVoucherCreationRequest request) {
        VoucherDateTime voucherDateTime = VoucherDateTime.of(LocalDateTime.now(), request.expirationDate());
        return Voucher.createPercentDiscount(UUID.randomUUID(), request.name(), request.minimumPriceCondition(), voucherDateTime, request.percent());
    }

    public static VoucherResponses toVoucherResponseList(List<Voucher> vouchers) {
        List<VoucherResponse> voucherResponses = vouchers.stream()
                .map(VoucherMapper::toVoucherResponse)
                .toList();
        return new VoucherResponses(voucherResponses);
    }

    private static VoucherResponse toVoucherResponse(Voucher voucher) {
        return new VoucherResponse(
                voucher.getName(),
                voucher.getMinimumPriceCondition(),
                voucher.getVoucherDate().getCreatedAt(),
                voucher.getVoucherDate().getExpiredAt()
        );
    }
}
