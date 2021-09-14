package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    private final Logger logger = LoggerFactory.getLogger(VoucherRestController.class);

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public List<VoucherEntity> restFindCustomers() {
        List<VoucherEntity> allVouchers = voucherService.findAll();
        return allVouchers;
    }



}
