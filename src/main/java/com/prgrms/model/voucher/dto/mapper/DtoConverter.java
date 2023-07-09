package com.prgrms.model.voucher.dto.mapper;

import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.dto.VoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoConverter {

    private DtoConverter() {
        throw new AssertionError("생성자를 호출할 수 없습니다.");
    }

    public List<VoucherResponse> convertVoucherResponse(Vouchers vouchers) {
        return vouchers.getVouchers()
                .stream()
                .map(VoucherResponse::new)
                .toList();
    }
}
