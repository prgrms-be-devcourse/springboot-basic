package com.devcourse.voucherapp.controller;

import com.devcourse.voucherapp.entity.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.dto.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.entity.dto.VouchersResponseDto;
import com.devcourse.voucherapp.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherResponseDto create(VoucherCreateRequestDto request) {
        return voucherService.create(request);
    }

    public VouchersResponseDto findAllVouchers() {
        return voucherService.findAllVouchers();
    }

    public VoucherResponseDto findVoucherById(String id) {
        return voucherService.findVoucherById(id);
    }

    public VoucherResponseDto update(VoucherUpdateRequestDto request) {
        return voucherService.update(request);
    }

    public void deleteById(String id) {
        voucherService.deleteById(id);
    }
}
