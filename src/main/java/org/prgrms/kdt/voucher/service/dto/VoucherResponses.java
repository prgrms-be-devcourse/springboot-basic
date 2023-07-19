package org.prgrms.kdt.voucher.service.dto;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public record VoucherResponses(List<VoucherResponse> vouchers) {

    public static VoucherResponses of(List<Voucher> vouchers) {
        List<VoucherResponse> vouchersResponse = vouchers.stream().map(VoucherResponse::new).collect(Collectors.toList());
        return new VoucherResponses(vouchersResponse);
    }

    @Override
    public List<VoucherResponse> vouchers() {
        return List.copyOf(vouchers);
    }
}
