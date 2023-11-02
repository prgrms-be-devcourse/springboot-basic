package com.programmers.springbootbasic.domain.voucher.presentation;

import com.programmers.springbootbasic.domain.voucher.application.VoucherService;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.UpdateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(CreateVoucherRequest request) {
        voucherService.create(request);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherService.findAll();
    }

    public VoucherResponse getVoucherById(UUID id) {
        return voucherService.findById(id);
    }

    public void deleteVoucherById(UUID id) {
        voucherService.deleteById(id);
    }

    public void updateVoucher(UUID id, UpdateVoucherRequest request) {
        voucherService.update(id, request);
    }

}
