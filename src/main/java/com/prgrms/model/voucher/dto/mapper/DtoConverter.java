package com.prgrms.model.voucher.dto.mapper;

import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.dto.VoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoConverter {

    public List<VoucherResponse> convertVoucherResponse(Vouchers vouchers) {
        return vouchers.getVouchers()
                .stream()
                .map(VoucherResponse::new)
                .toList();
    }
}
