package com.programmers.wallet.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.wallet.dto.WalletRequestDto;
import com.programmers.wallet.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    public VouchersResponseDto findByCustomerId(UUID customerId) {
        List<Voucher> vouchers = jdbcWalletRepository.findByCustomerId(customerId);

        return new VouchersResponseDto(vouchers);
    }
}
