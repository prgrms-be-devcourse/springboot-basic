package com.programmers.console.controller;

import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponseDto create (VoucherRequestDto requestDto) {
        return voucherService.create(requestDto);
    }

    public List<VoucherResponseDto> findAll() {
        return voucherService.findVouchers();
    }
}
