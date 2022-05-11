package com.prgrms.kdt.springbootbasic.voucher.controller;

import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@Profile("local")
public class ConsoleVoucherController {
    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(ConsoleVoucherController.class);

    public ConsoleVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public List<Voucher> getVoucherList() {
        return voucherService.getAllVouchers();
    }


    public boolean saveVoucher(String voucherType, UUID voucherId, long discountAmount){
        Voucher voucher = voucherService.saveVoucher(voucherType,discountAmount);
        return true;
    }

}


