package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WalletService {

    private final JdbcWalletRepository jdbcWalletRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(JdbcWalletRepository jdbcWalletRepository, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.jdbcWalletRepository = jdbcWalletRepository;
        this.voucherRepository = voucherRepository; // walletService가 voucherRepository & customerRepository 를 주입받아도 되나요..?
        this.customerRepository = customerRepository;
    }

    public boolean insertToWallet(UUID customerId, UUID voucherId) {
        if (customerIsEmpty(customerId) || voucherIsEmpty(voucherId)) return false;
        return jdbcWalletRepository.insertToWallet(customerId, voucherId);
    }

    public List<UUID> findVoucherIdsByCustomerId(UUID customerId) {
        if(customerIsEmpty(customerId)) return List.of();
        return jdbcWalletRepository.findVoucherIdsByCustomerId(customerId);
    }

    public Optional<UUID> findCustomerIdByVoucherId(UUID voucherId) {
        if(voucherIsEmpty(voucherId)) return Optional.empty();
        return jdbcWalletRepository.findCustomerIdByVoucherId(voucherId);
    }

    public boolean deleteVoucherFromWallet(UUID voucherId) {
        if(voucherIsEmpty(voucherId)) return false;
        return jdbcWalletRepository.deleteVoucher(voucherId);
    }

    private boolean voucherIsEmpty(UUID voucherId) {
        return voucherRepository.findById(voucherId).isEmpty();
    }

    private boolean customerIsEmpty(UUID customerId) {
        return customerRepository.findById(customerId).isEmpty();
    }

}
