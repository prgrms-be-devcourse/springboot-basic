package com.prgrms.voucher.service;

import com.prgrms.voucher.model.Vouchers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherConverter {

    private VoucherConverter() { }

    public List<VoucherResponse> convertVoucherResponses(Vouchers vouchers) {
        return vouchers.vouchers()
                .stream()
                .map(VoucherResponse::new)
                .toList();
    }

}
