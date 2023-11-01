package com.programmers.vouchermanagement.wallet.service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public Customer readCustomerByVoucherId(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId).orElseThrow(
                () -> {
                    logger.error(NOT_FOUND_VOUCHER_ALLOCATION);
                    return new NoSuchElementException(NOT_FOUND_VOUCHER_ALLOCATION);
                }
        );
    }

    public List<Voucher> readAllVoucherByCustomerId(UUID customerId) {
        return walletRepository.findAllVoucherByCustomerId(customerId);
    }

    public void deleteVoucherFromCustomer(UUID voucherId) {
        walletRepository.delete(voucherId);
    }

    public void deleteAllAllocation() {
        walletRepository.deleteAll();
    }
}
