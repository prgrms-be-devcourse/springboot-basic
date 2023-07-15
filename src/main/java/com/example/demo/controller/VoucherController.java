package com.example.demo.controller;

import com.example.demo.dto.VoucherDto;
import com.example.demo.service.VoucherService;
import com.example.demo.util.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;


    public VoucherDto create(VoucherDiscountType voucherDiscountType, int amount) {
        NumberValidator.validateAmount(voucherDiscountType, String.valueOf(amount));
        return voucherService.save(voucherDiscountType, amount);
    }

    public List<VoucherDto> readList() {
        return voucherService.readVoucherList();
    }

    public VoucherDto read(UUID id) {
        return voucherService.read(id);
    }

    public void updateAmount(UUID id, int discountAmount) {
        voucherService.updateAmount(id, discountAmount);
    }

    public void delete(UUID id) {
        voucherService.delete(id);
    }

    public VoucherDiscountType checkDiscountType(UUID id) {
        voucherService.checkDiscountType(id);
    }
}

