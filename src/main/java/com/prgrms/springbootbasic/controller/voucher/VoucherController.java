package com.prgrms.springbootbasic.controller.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.service.VoucherService;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherType type, long discount) {
        voucherService.createVoucher(type, discount);
    }

    public Map<UUID, Voucher> printVoucherList() {
        return voucherService.fetchAllVouchers();
    }
}
