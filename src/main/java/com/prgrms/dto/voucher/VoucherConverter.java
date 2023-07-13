package com.prgrms.dto.voucher;

import com.prgrms.model.voucher.Vouchers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherConverter {

    private VoucherConverter() {
    }

    public List<VoucherResponse> convertVoucherResponse(Vouchers vouchers) {
        return vouchers.getVouchers()
                .stream()
                .map(VoucherResponse::new)
                .toList();
    }

}
