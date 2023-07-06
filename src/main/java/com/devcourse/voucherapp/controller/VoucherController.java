package com.devcourse.voucherapp.controller;

import com.devcourse.voucherapp.entity.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.dto.VouchersResponseDto;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.service.VoucherService;
import java.util.List;
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
}
