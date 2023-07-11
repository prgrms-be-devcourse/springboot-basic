package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherDto createVoucher(VoucherDto voucherDto) {
        return VoucherDto.of(
                voucherService.createVoucher(
                        VoucherDto.to(voucherDto)
                )
        );
    }

    public VoucherDto updateVoucher(VoucherDto voucherDto) {
        return VoucherDto.of(
                voucherService.updateVoucher(
                        VoucherDto.to(voucherDto)
                )
        );
    }

    public List<VoucherDto> findAllVouchers() {
        return voucherService.getVouchers().stream()
                .map(VoucherDto::of)
                .toList();
    }

    public VoucherDto findVoucherById(UUID voucherId) {
        return VoucherDto.of(voucherService.findVoucherById(voucherId));
    }

    public void deleteVouchers() {
        voucherService.deleteAllVouchers();
    }

    public VoucherDto deleteVoucherById(UUID voucherId) {
        return VoucherDto.of(voucherService.deleteVoucherById(voucherId));
    }

}
