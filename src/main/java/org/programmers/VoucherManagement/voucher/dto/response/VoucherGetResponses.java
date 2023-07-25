package org.programmers.VoucherManagement.voucher.dto.response;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VoucherGetResponses {
    private final List<VoucherGetResponse> voucherResponses;

    public VoucherGetResponses(List<Voucher> vouchers) {
        this.voucherResponses = vouchers
                .stream()
                .map(VoucherGetResponse::toDto)
                .collect(Collectors.toList());
    }

    public List<VoucherGetResponse> getGetVoucherListRes() {
        return Collections.unmodifiableList(voucherResponses);
    }
}
