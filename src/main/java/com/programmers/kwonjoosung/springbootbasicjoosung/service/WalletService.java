package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletService {


    private final JdbcWalletRepository jdbcWalletRepository;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public WalletService(JdbcWalletRepository jdbcWalletRepository, CustomerService customerService, VoucherService voucherService) {
        this.jdbcWalletRepository = jdbcWalletRepository;
        this.customerService = customerService;
        this.voucherService = voucherService;
    }


    public boolean assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        return jdbcWalletRepository.insert(customerId, voucherId) == 1;
    }

    public Customer findCustomerByVoucherId(UUID voucherId) {
        UUID customerId = jdbcWalletRepository.findByVoucherId(voucherId);
        return customerService.findCustomerByCustomerId(customerId);
    }

    public List<Voucher> findAllVouchersFromWallet(UUID customerId) {
        return jdbcWalletRepository.findByCustomerId(customerId)
                .stream()
                .map(voucherService::findVoucher)
                .collect(Collectors.toList());
    }

    public boolean deleteVoucherFromWallet(UUID voucherId) {
        return jdbcWalletRepository.delete(voucherId) == 1;
    }

}
