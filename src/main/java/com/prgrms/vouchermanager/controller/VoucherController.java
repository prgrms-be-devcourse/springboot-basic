package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class VoucherController {
    private final VoucherService service;

    public VoucherController(VoucherService service) {
        this.service = service;
    }

    public void create(VoucherType voucherType, long discount) {
        service.create(voucherType, discount);
    }

    public List<Voucher> list() {
        return service.list();
    }

    public List<Customer> blacklist() {
        return service.blacklist();
    }
}
