package com.prgrms.voucher.service.mapper;

import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherConverter {

    private VoucherConverter() { }

    public List<VoucherServiceResponse> convertVoucherResponses(Vouchers vouchers) {
        return vouchers.vouchers()
                .stream()
                .map(VoucherServiceResponse::new)
                .toList();
    }

}
