package com.programmers.springmission.voucher.presentation;

import com.programmers.springmission.voucher.application.VoucherService;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return voucherService.createVoucher(voucherCreateRequest);
    }

    public List<VoucherResponse> findAllVoucher() {
        return voucherService.findAllVoucher();
    }
}

