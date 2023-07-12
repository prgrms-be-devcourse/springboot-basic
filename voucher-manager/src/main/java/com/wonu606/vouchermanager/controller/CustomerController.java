package com.wonu606.vouchermanager.controller;

import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerDto;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.customerVoucherWallet.CustomerVoucherWalletService;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final CustomerVoucherWalletService walletService;

    public CustomerController(CustomerService customerService, VoucherService voucherService,
            CustomerVoucherWalletService walletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    public List<Customer> getCustomerList() {
        return customerService.getCustomerList();
    }

    List<Voucher> getVouchersOwnedByCustomer(CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto);
        List<UUID> voucherIdList = walletService.findVoucherIdByCustomer(customer);
        return voucherService.getVoucherList(voucherIdList);
    }

    public void deleteWallet(String emailAddress, String voucherId) {
        walletService.deleteByWallet(
                new CustomerVoucherWalletDto(UUID.fromString(voucherId), emailAddress));
    }
}
