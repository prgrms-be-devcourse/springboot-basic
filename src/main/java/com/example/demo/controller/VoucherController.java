package com.example.demo.controller;

import com.example.demo.dto.voucher.VoucherResponseDto;
import com.example.demo.service.VoucherService;
import com.example.demo.util.VoucherType;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponseDto create(VoucherType voucherType, int amount) {
        return voucherService.save(voucherType, amount);
    }

    public List<VoucherResponseDto> readList() {
        return voucherService.readVoucherList();
    }

    public VoucherResponseDto read(UUID id) {
        return voucherService.read(id);
    }
}

