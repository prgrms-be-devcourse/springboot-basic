package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.customer.Customer;
import com.example.voucher_manager.domain.service.CustomerService;
import com.example.voucher_manager.domain.service.VoucherService;
import com.example.voucher_manager.domain.voucher.Voucher;
import com.example.voucher_manager.domain.voucher.VoucherType;

import java.util.List;

public class CommandOperator {
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandOperator(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public Voucher create(VoucherType voucherType, Long discountInformation) {
        return voucherService.createVoucher(voucherType, discountInformation);
    }

    public List<Voucher> getVoucherList() {
        return voucherService.findAll();
    }

    public List<Customer> getBlacklist() {
        return customerService.findAll();
    }
}
