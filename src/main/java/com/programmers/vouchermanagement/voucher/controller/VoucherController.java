package com.programmers.vouchermanagement.voucher.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequestDTO;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    //TODO: Response DTO and consider conversion to text format for GUI response

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher create(CreateVoucherRequestDTO createVoucherRequestDTO) {
        return voucherService.create(createVoucherRequestDTO);
    }

    public List<Voucher> readAllVouchers() {
        return voucherService.readAllVouchers();
    }
}
