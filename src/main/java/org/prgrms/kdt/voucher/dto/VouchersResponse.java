package org.prgrms.kdt.voucher.dto;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public class VouchersResponse {
    private final List<VoucherResponse> vouchers;

    public VouchersResponse(List<VoucherResponse> vouchers) {
        this.vouchers = vouchers;
    }

    public static VouchersResponse of(List<Voucher> vouchers) {
        List<VoucherResponse> vouchersResponse = vouchers.stream().map(VoucherResponse::new).collect(Collectors.toList());
        return new VouchersResponse(vouchersResponse);
    }

    public List<VoucherResponse> getVouchers(){
        return List.copyOf(vouchers);
    }
}
