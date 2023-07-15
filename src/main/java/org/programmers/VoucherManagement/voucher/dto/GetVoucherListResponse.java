package org.programmers.VoucherManagement.voucher.dto;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetVoucherListResponse {
    private final List<GetVoucherResponse> voucherResponses;

    public GetVoucherListResponse(List<Voucher> vouchers) {
        this.voucherResponses = vouchers
                .stream()
                .map(GetVoucherResponse::toDto)
                .collect(Collectors.toList());
    }


    public List<GetVoucherResponse> getGetVoucherListRes() {
        return Collections.unmodifiableList(voucherResponses);
    }
}
