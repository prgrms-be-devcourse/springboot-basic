package com.example.voucher.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.service.VoucherService;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createVoucher(VoucherType voucherType, long discountValue) {
        return voucherService.createVoucher(voucherType, discountValue);
    }

    public List<VoucherDTO> getVouchers() {
        return voucherService.getVouchers()
            .stream()
            .map(o -> new VoucherDTO(o.getValue(), o.getVoucherType()))
            .toList();
    }

    public void deleteVouchers() {
        voucherService.deleteVouchers();
    }

}
