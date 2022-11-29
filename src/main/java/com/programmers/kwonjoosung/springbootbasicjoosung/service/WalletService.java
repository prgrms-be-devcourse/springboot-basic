package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.customer.CustomerDto;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.VoucherDto;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final JdbcWalletRepository jdbcWalletRepository;

    public WalletService(JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public void insertToWallet(UUID customerId, UUID voucherId) {
        jdbcWalletRepository.insertToWallet(customerId, voucherId);
    }

    public List<VoucherDto> findWalletByCustomerId(UUID customerId) {
        return jdbcWalletRepository.findVouchersByCustomerId(customerId)
                .stream()
                .map(VoucherDto::new)
                .toList();
    }

    public CustomerDto findCustomerByVoucherId(UUID voucherId) {
        return new CustomerDto(jdbcWalletRepository.findCustomerByVoucherId(voucherId));
    }

    public void deleteVoucherFromWallet(UUID voucherId) {
        jdbcWalletRepository.deleteVoucher(voucherId);
    }
}
