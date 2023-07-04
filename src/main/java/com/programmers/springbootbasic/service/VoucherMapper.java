package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.service.dto.FixedAmountVoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.PercentDiscountVoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.VoucherResponse;
import com.programmers.springbootbasic.service.dto.VoucherResponses;

import java.time.LocalDateTime;
import java.util.List;

public final class VoucherMapper {
    private VoucherMapper() {

    }

    public static Voucher getFixedAmountVoucher(FixedAmountVoucherCreationRequest request) {
        return VoucherFactory.of(request.type(), request.name(), request.minimumPriceCondition(), LocalDateTime.now(), request.expirationDate(), request.amount());
    }

    public static Voucher getPercentDiscountVoucher(PercentDiscountVoucherCreationRequest request) {
        return VoucherFactory.of(request.type(), request.name(), request.minimumPriceCondition(), LocalDateTime.now(), request.expirationDate(), request.percent());
    }

    public static VoucherResponses getVoucherResponseList(List<Voucher> vouchers) {
        List<VoucherResponse> voucherResponses = vouchers.stream()
                .map(VoucherMapper::getVoucherResponse)
                .toList();
        return new VoucherResponses(voucherResponses);
    }

    private static VoucherResponse getVoucherResponse(Voucher voucher) {
        return new VoucherResponse(
                voucher.getName(),
                voucher.getMinimumPriceCondition(),
                voucher.getVoucherDate().getCreatedDate(),
                voucher.getVoucherDate().getExpirationDate()
        );
    }
}
