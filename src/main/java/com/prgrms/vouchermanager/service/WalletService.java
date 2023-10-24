package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.domain.Wallet;
import com.prgrms.vouchermanager.repository.CustomerRepository;
import com.prgrms.vouchermanager.repository.VoucherRepository;
import com.prgrms.vouchermanager.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public WalletService(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public Wallet create(UUID customerId, UUID voucherId) {
        Wallet wallet = new Wallet(voucherId, customerId);
        walletRepository.create(wallet);
        return wallet;
    }

    public List<Voucher> findByCustomerId(UUID id) {
        List<Wallet> walletList = walletRepository.findByCustomerId(id);
        List<Voucher> voucherList = new ArrayList<>();

        walletList.forEach(wallet -> {
            UUID voucherId = wallet.getVoucherId();
            voucherList.add(voucherRepository.findById(voucherId));
        });
        return voucherList;
    }

    public List<Customer> findByVoucherId(UUID id) {
        List<Wallet> walletList = walletRepository.findByVoucherId(id);
        List<Customer> customerList = new ArrayList<>();

        walletList.forEach(wallet -> {
            UUID customerId = wallet.getCustomerId();
            customerList.add(customerRepository.findById(customerId));
        });
        return customerList;
    }

    public void delete(UUID customerId, UUID voucherId) {
        walletRepository.delete(customerId, voucherId);
    }
}
