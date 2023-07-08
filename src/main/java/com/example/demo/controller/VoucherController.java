package com.example.demo.controller;

import com.example.demo.dto.VoucherDto;
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

    public VoucherDto create(VoucherType voucherType, int amount) {
        return voucherService.save(voucherType, amount);
    }

    public List<VoucherDto> readList() {
        return voucherService.readVoucherList();
    }

    public VoucherDto read(UUID id) {
        return voucherService.read(id);
    }
}

