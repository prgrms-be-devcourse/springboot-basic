package com.prgrms.springbasic.domain.voucher.controller;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse saveVoucher(CreateVoucherRequest request) {
        return voucherService.saveVoucher(request);
    }

    public List<VoucherResponse> findAll() {
        return voucherService.findAll();
    }
}
