package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VoucherConsoleController {
    private final VoucherService service;

    public Voucher create(VoucherType voucherType, long discount) {
        return service.create(voucherType, discount);
    }

    @GetMapping
    public List<Voucher> findAll() {
        return service.findAll();
    }

    public Voucher updateDiscount(UUID id, int discount) {
        return service.updateDiscount(id, discount);
    }

    public int delete(UUID id) {
        return service.delete(id);
    }
}
