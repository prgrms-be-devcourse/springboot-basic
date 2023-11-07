package com.programmers.vouchermanagement.wallet.service;

import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.programmers.vouchermanagement.util.Message.NOT_FOUND_VOUCHER_ALLOCATION;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void allocate(Ownership ownership) {
        walletRepository.insert(ownership);
    }

    @Transactional(readOnly = true)
    public CustomerResponse readCustomerByVoucherId(UUID voucherId) {
        Optional<Customer> customerOptional = walletRepository.findCustomerByVoucherId(voucherId);
        Customer customer = customerOptional.orElseThrow(() -> {
            logger.error(NOT_FOUND_VOUCHER_ALLOCATION);
            return new NoSuchElementException(NOT_FOUND_VOUCHER_ALLOCATION);
        });
        return CustomerResponse.from(customer);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> readAllVoucherByCustomerId(UUID customerId) {
        List<Voucher> vouchers = walletRepository.findAllVoucherByCustomerId(customerId);
        if (vouchers.isEmpty()) return Collections.emptyList();
        return vouchers.stream().map(VoucherResponse::from).toList();
    }

    @Transactional
    public void deleteVoucherFromCustomer(UUID voucherId) {
        walletRepository.delete(voucherId);
    }

    @Transactional
    public void deleteAllAllocation() {
        walletRepository.deleteAll();
    }
}
