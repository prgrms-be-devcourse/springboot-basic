package com.kdt.commandLineApp.service.voucherWallet;

import com.kdt.commandLineApp.service.customer.Customer;
import com.kdt.commandLineApp.service.voucher.Voucher;
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

    public void giveVoucherToCustomer(long customerId, long voucherId) {
        voucherWalletRepository.giveVoucherToCustomer(customerId, voucherId);
    }

    public void deleteVoucherFromCustomer(long customerId, long voucherId) {
        voucherWalletRepository.deleteVoucherFromCustomer(customerId, voucherId);
    }

    public List<Voucher> getCustomerVouchers(long customerId) {
        return voucherWalletRepository.getCustomerVouchers(customerId);
    }

    public List<Customer> getCustomersWithVoucherId(long voucherId) {
        return voucherWalletRepository.getCustomersWithVoucherId(voucherId);
    }
}
