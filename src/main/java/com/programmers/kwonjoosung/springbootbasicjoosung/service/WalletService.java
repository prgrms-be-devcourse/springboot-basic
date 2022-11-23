package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
        if (jdbcWalletRepository.findCustomerIdByVoucherId(voucherId).isPresent()) return false;
        return jdbcWalletRepository.insertToWallet(customerId, voucherId);
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        if (customerIsEmpty(customerId)) return List.of();
        return jdbcWalletRepository
                .findVoucherIdsByCustomerId(customerId)
                .stream()
                .map(voucherRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        if (voucherIsEmpty(voucherId)) return Optional.empty();
        Optional<UUID> customerIdByVoucherId = jdbcWalletRepository.findCustomerIdByVoucherId(voucherId);
        if (customerIdByVoucherId.isEmpty()) return Optional.empty();
        return customerRepository.findById(customerIdByVoucherId.get());
    }

    public boolean deleteVoucherFromWallet(UUID voucherId) {
        if (voucherIsEmpty(voucherId)) return false;
        return jdbcWalletRepository.deleteVoucher(voucherId);
    }

    private boolean voucherIsEmpty(UUID voucherId) {
        return voucherRepository.findById(voucherId).isEmpty();
    }

    private boolean customerIsEmpty(UUID customerId) {
        return customerRepository.findById(customerId).isEmpty();
    }

}
