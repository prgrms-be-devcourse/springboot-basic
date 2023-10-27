package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.common.ErrorMessage;
import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class WalletService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);

    public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }

    public List<Voucher> findVoucherByCustomer(UUID customerId) {
        return walletRepository.findByCustomerId(customerId).stream()
                .map(wallet -> voucherRepository.findById(wallet.getVoucherId())
                        .orElseThrow(() -> new NoSuchElementException(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage() +
                                ":" + wallet.getVoucherId())))
                .toList();
    }

    public List<Customer> findCustomerByVoucher(UUID voucherId) {
        return walletRepository.findByCustomerId(voucherId).stream()
                .map(wallet -> customerRepository.findById(wallet.getCustomerId())
                        .orElseThrow(() -> new NoSuchElementException(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage() +
                                ":" + wallet.getCustomerId())))
                .toList();
    }

    public Wallet giveVoucherToCustomer(UUID customerId, UUID voucherId) {
        return walletRepository.save(new Wallet(UUID.randomUUID(), customerId, voucherId));
    }

    public void deleteVoucherFromCustomer(UUID customerId, UUID voucherId) {
        int affectedRow = walletRepository.delete(new Wallet(UUID.randomUUID(), customerId, voucherId).getId());
        if (affectedRow == 0) {
            throw new NoSuchElementException(ErrorMessage.WALLET_NOT_FOUND_MESSAGE.getMessage());
        }
    }
}
