package org.prgrms.kdt.voucher.dto;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public record VouchersResponse(List<VoucherResponse> vouchers) {

    public static VouchersResponse of(List<Voucher> vouchers) {
        List<VoucherResponse> vouchersResponse = vouchers.stream().map(VoucherResponse::new).collect(Collectors.toList());
        return new VouchersResponse(vouchersResponse);
    }

    @Override
    public List<VoucherResponse> vouchers() {
        return List.copyOf(vouchers);
    }
}
