package com.prgrms.kdt.springbootbasic.controller;

import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public List<Voucher> getVoucherList() {
        return voucherService.getAllVouchers();
    }


    public boolean saveVoucher(String voucherType, UUID voucherId, long discountAmount){
        Voucher voucher = voucherService.createVoucher(voucherType,voucherId, discountAmount);
        return voucherService.saveVoucher(voucher).isPresent();
    }

}


