package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;

import java.util.List;
import java.util.UUID;

public final class VoucherMapper {
    private VoucherMapper() {

    }

    public static Voucher toVoucher(VoucherCreationRequest request) {
        VoucherType voucherType = request.voucherType();
        return Voucher.createVoucher(UUID.randomUUID(), voucherType, request.amountOrPercent());
    }

    public static VoucherResponses toVoucherResponseList(List<Voucher> vouchers) {
        List<VoucherResponse> voucherResponses = vouchers.stream()
                .map(VoucherMapper::toVoucherResponse)
                .toList();
        return new VoucherResponses(voucherResponses);
    }

    public static VoucherResponse toVoucherResponse(Voucher voucher) {
        voucher.getAmountOrPercent();
        return new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getVoucherType(),
                voucher.getAmountOrPercent()
        );
    }

}
