package com.example.voucher.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.service.VoucherService;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherDTO createVoucher(VoucherRequest.Create request) {
        return voucherService.createVoucher(request.getVoucherType(), request.getDiscountValue());
    }

    public List<VoucherDTO> getVouchers() {
        return voucherService.getVouchers();
    }

    public void deleteVouchers() {
        voucherService.deleteVouchers();
    }

    public VoucherDTO search(UUID voucherId) {
        return voucherService.search(voucherId);
    }

    public VoucherDTO update(VoucherRequest.Update request) {
        return voucherService.update(request.getVoucherId(), request.getVoucherType(), request.getDiscountValue());
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

}
