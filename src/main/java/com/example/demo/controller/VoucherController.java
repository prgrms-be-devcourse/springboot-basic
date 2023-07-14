package com.example.demo.controller;

import com.example.demo.dto.VoucherDto;
import com.example.demo.service.VoucherService;
import com.example.demo.util.VoucherType;
import com.example.demo.view.validate.NumberValidator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;


    public VoucherDto create(VoucherType voucherType, int amount) {
        NumberValidator.validateAmount(voucherType, String.valueOf(amount));
        return voucherService.save(voucherType, amount);
    }

    public List<VoucherDto> readList() {
        return voucherService.readVoucherList();
    }

    public VoucherDto read(UUID id) {
        return voucherService.read(id);
    }
}

