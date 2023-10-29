package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class VoucherController {
    private final VoucherService service;

    public VoucherController(VoucherService service) {
        this.service = service;
    }

    public Voucher create(VoucherType voucherType, long discount) {
        return service.create(voucherType, discount);
    }

    public List<Voucher> list() {
        return service.findAll();
    }

    public Voucher updateDiscount(UUID id, int discount) {
        return service.updateDiscount(id, discount);
    }

    public int delete(UUID id) {
        return service.delete(id);
    }
}
