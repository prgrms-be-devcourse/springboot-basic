package com.example.voucher.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.service.VoucherService;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherDTO createVoucher(VoucherType voucherType, long discountValue) {
        return voucherService.createVoucher(voucherType, discountValue);
    }

    public List<VoucherDTO> getVouchers() {
        return voucherService.getVouchers();
    }

    public void deleteVouchers() {
        voucherService.deleteVouchers();
    }

    public VoucherDTO searchById(UUID voucherId) {
        return voucherService.searchById(voucherId);
    }

    public VoucherDTO update(UUID voucherId, VoucherType voucherType, long discountValue) {
        return voucherService.update(voucherId, voucherType, discountValue);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

}
