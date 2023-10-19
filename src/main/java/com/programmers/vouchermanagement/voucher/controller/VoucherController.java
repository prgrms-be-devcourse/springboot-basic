package com.programmers.vouchermanagement.voucher.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequestDTO;
import com.programmers.vouchermanagement.voucher.dto.GeneralVoucherDTO;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public GeneralVoucherDTO create(CreateVoucherRequestDTO createVoucherRequestDTO) {
        return voucherService.create(createVoucherRequestDTO);
    }

    public List<GeneralVoucherDTO> readAllVouchers() {
        return voucherService.readAllVouchers();
    }
}
