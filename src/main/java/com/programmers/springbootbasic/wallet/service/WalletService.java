package com.programmers.springbootbasic.wallet.service;

import com.programmers.springbootbasic.customer.domain.Customer;
import com.programmers.springbootbasic.customer.dto.CustomerResponseDto;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.wallet.dto.WalletDto;
import com.programmers.springbootbasic.wallet.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        Optional<Customer> customerWrapper = jdbcWalletRepository.findCustomerByVoucherId(voucherId);

        if (customerWrapper.isPresent()) {
            Customer customer = customerWrapper.get();
            return new CustomerResponseDto(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerType());
        }

        return new CustomerResponseDto(null, null, null);
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
