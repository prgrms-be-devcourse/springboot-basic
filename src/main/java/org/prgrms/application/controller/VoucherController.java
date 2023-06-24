package org.prgrms.application.controller;

import org.prgrms.application.domain.Voucher;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Map<UUID, Voucher> getStorage() {
        return voucherService.getVoucherList();
    }


//    public void createVoucher(String voucherType) {
//        VoucherType voucherType = VoucherType.valueOf(selection);
//        voucherService.create(voucherType);
//    }
}