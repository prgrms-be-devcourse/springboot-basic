package com.wonu606.vouchermanager.controller;

import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerDto;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherWalletService walletService;

    public CustomerController(CustomerService customerService, VoucherService voucherService,
            VoucherWalletService walletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto);
        return customerService.saveCustomer(customer);
    }

    public List<Customer> getCustomerList() {
        return customerService.getCustomerList();
    }

    public List<Voucher> getVouchersOwnedByCustomer(String emailAddress) {
        List<UUID> voucherIdList = walletService.findVoucherIdListByCustomerEmailAddress(
                new Email(emailAddress));
        return voucherService.getVoucherList(voucherIdList);
    }

    public void deleteWallet(String emailAddress, String voucherId) {
        walletService.deleteByWallet(
                new VoucherWalletDto(UUID.fromString(voucherId), emailAddress));
    }
}
