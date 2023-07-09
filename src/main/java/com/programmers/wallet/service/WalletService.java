package com.programmers.wallet.service;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.wallet.dto.WalletDto;
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
    public void updateVoucherCustomerId(WalletDto voucherDto) {
        jdbcWalletRepository.updateVoucherCustomerId(voucherDto.customerId(), voucherDto.voucherId());
    }

    public VouchersResponseDto findVouchersByCustomerId(UUID customerId) {
        List<Voucher> vouchers = jdbcWalletRepository.findVouchersByCustomerId(customerId);

        return new VouchersResponseDto(vouchers);
    }

    public CustomerResponseDto findCustomerByVoucherId(UUID voucherId) {
        Customer customer = jdbcWalletRepository.findCustomerByVoucherId(voucherId).get();

        return new CustomerResponseDto(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerType());
    }

    @Transactional
    public void deleteVoucherByVoucherIdAndCustomerId(WalletDto voucherDto) {
        jdbcWalletRepository.deleteVoucherByVoucherIdAndCustomerId(voucherDto.voucherId(), voucherDto.customerId());
    }

    @Transactional
    public void deleteAllVouchersByCustomerId(UUID customerId) {
        jdbcWalletRepository.deleteAllVouchersByCustomerId(customerId);
    }
}
