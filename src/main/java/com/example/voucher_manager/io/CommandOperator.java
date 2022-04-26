package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.customer.Customer;
import com.example.voucher_manager.domain.customer.CustomerService;
import com.example.voucher_manager.domain.voucher.VoucherService;
import com.example.voucher_manager.domain.voucher.Voucher;
import com.example.voucher_manager.domain.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        return customerService.findAllBlackList();
    }

    public List<Customer> getCustomerList(){
        return customerService.findAll();
    }

    public Customer signUp(String name, String email) {
        return customerService.signUp(name, email);
    }
}
