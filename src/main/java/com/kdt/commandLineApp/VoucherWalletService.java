package com.kdt.commandLineApp;

import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherWalletService {
    @Autowired
    VoucherWalletRepository voucherWalletRepository;

    public void giveVoucherToCustomer(String customerId, String voucherId) {
        voucherWalletRepository.giveVoucherToCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
    }

    public void deleteVoucherFromCustomer(String customerId, String voucherId) {
        voucherWalletRepository.deleteVoucherFromCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
    }

    public List<Voucher> getCustomerVouchers(String customerId) {
        return voucherWalletRepository.getCustomerVouchers(UUID.fromString(customerId));
    }

    public List<Customer> getCustomersWithVoucherId(String voucherId) {
        return voucherWalletRepository.getCustomersWithVoucherId(UUID.fromString(voucherId));
    }
}
