package com.programmers.wallet.service;

import com.programmers.wallet.dto.WalletRequestDto;
import com.programmers.wallet.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final JdbcWalletRepository jdbcWalletRepository;

    public WalletService(JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    @Transactional
    public void updateCustomerId(WalletRequestDto voucherDto) {
        jdbcWalletRepository.updateCustomerId(voucherDto.customerId(), voucherDto.voucherId());
    }
}
