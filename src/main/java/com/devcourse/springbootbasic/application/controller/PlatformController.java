package com.devcourse.springbootbasic.application.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.customer.service.CustomerService;
import com.devcourse.springbootbasic.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlatformController {


    private final VoucherService voucherService;
    private final CustomerService customerService;

    public PlatformController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void createVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherService.getVouchers();
    }

    public List<Customer> getBlackCustomers() {
        return customerService.getBlackCustomers();
    }
}
