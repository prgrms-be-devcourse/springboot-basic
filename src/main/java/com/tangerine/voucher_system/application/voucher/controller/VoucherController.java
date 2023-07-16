package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
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
                voucherService.createVoucher(voucherDto.to())
        );
    }

    public VoucherDto updateVoucher(VoucherDto voucherDto) {
        return VoucherDto.of(
                voucherService.updateVoucher(voucherDto.to())
        );
    }

    public List<VoucherDto> voucherList() {
        return voucherService.findVouchers().stream()
                .map(VoucherDto::of)
                .toList();
    }

    public VoucherDto voucherById(UUID voucherId) {
        return VoucherDto.of(voucherService.findVoucherById(voucherId));
    }

    public VoucherDto voucherByType(VoucherType voucherType) {
        return VoucherDto.of(voucherService.findVoucherByVoucherType(voucherType));
    }

    public VoucherDto voucherByCreatedAt(LocalDateTime createdAt) {
        return VoucherDto.of(voucherService.findVoucherByCreatedAt(createdAt));
    }

    public VoucherDto deleteVoucherById(UUID voucherId) {
        return VoucherDto.of(voucherService.deleteVoucherById(voucherId));
    }

}
