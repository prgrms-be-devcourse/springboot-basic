package com.program.commandLine.service;

import org.springframework.stereotype.Service;

@Service
public class VoucherWalletService {

    private final VoucherService voucherService;
    private final CustomerService customerService;


    public VoucherWalletService(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }


}
