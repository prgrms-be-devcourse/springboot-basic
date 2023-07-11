package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

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
                        voucherDto.to(voucherDto)
                )
        );
    }

    public List<VoucherDto> getAllVouchers() {
        return voucherService.getVouchers().stream()
                .map(VoucherDto::of)
                .toList();
    }

    public VoucherDto findVoucherById(VoucherDto voucherDto) {
        return VoucherDto.of(voucherService.findVoucherById(VoucherDto.to(voucherDto)));
    }

    public void deleteVouchers() {
        voucherService.deleteAllVouchers();
    }

    public VoucherDto deleteVoucherById(VoucherDto voucherDto) {
        return VoucherDto.of(voucherService.deleteVoucherById(VoucherDto.to(voucherDto)));
    }

}
