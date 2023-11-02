package com.programmers.vouchermanagement.wallet.service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerDto;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.programmers.vouchermanagement.constant.Message.NOT_FOUND_VOUCHER_ALLOCATION;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void allocate(Ownership ownership) {
        walletRepository.save(ownership);
    }

    public CustomerDto readCustomerByVoucherId(UUID voucherId) {
        Customer customer = walletRepository.findCustomerByVoucherId(voucherId).orElseThrow(() -> {
            logger.error(NOT_FOUND_VOUCHER_ALLOCATION);
            return new NoSuchElementException(NOT_FOUND_VOUCHER_ALLOCATION);
        });
        return CustomerDto.from(customer);
    }

    public List<VoucherDto> readAllVoucherByCustomerId(UUID customerId) {
        List<Voucher> vouchers = walletRepository.findAllVoucherByCustomerId(customerId);
        if (vouchers.isEmpty()) return Collections.emptyList();
        return vouchers.stream().map(VoucherDto::from).toList();
    }

    public void deleteVoucherFromCustomer(UUID voucherId) {
        walletRepository.delete(voucherId);
    }

    public void deleteAllAllocation() {
        walletRepository.deleteAll();
    }
}
