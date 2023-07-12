package com.programmers.voucher.controller;

import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.dto.ServiceDto;
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
        ServiceDto serviceDto = VoucherMapper.convertRequestDtoToServiceDto(requestDto);
        return voucherService.create(serviceDto);
    }

    public List<VoucherResponseDto> findAll() {
        return voucherService.findVouchers();
    }
}
