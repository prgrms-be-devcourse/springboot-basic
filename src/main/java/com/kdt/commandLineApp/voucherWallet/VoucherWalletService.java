package com.kdt.commandLineApp.voucherWallet;

import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherWalletService {
    private VoucherWalletRepository voucherWalletRepository;

    @Autowired
    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public void giveVoucherToCustomer(String customerId, String voucherId) {
        voucherWalletRepository.giveVoucherToCustomer(customerId, voucherId);
    }

    public void deleteVoucherFromCustomer(String customerId, String voucherId) {
        voucherWalletRepository.deleteVoucherFromCustomer(customerId, voucherId);
    }

    public List<Voucher> getCustomerVouchers(String customerId) {
        return voucherWalletRepository.getCustomerVouchers(customerId);
    }

    public List<Customer> getCustomersWithVoucherId(String voucherId) {
        return voucherWalletRepository.getCustomersWithVoucherId(voucherId);
    }
}
