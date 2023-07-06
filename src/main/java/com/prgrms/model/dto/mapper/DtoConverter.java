package com.prgrms.mapper;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoConverter {
    public List<VoucherResponse> convertVoucherResponse(VoucherRegistry vouchers) {
        return  vouchers.getVoucherRegistry()
                .stream()
                .map(VoucherResponse::new)
                .toList();
    }
}
