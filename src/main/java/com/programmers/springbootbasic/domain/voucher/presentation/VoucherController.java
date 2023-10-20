package com.programmers.springbootbasic.domain.voucher.presentation;

import com.programmers.springbootbasic.domain.voucher.application.VoucherService;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(CreateVoucherRequest request) {
        voucherService.createVoucher(request);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherService.findAllVouchers();
    }

}
