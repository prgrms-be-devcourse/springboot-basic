package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class WalletService {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletRepository walletRepository;

    public WalletService(VoucherService voucherService, CustomerService customerService, WalletRepository walletRepository) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletRepository = walletRepository;
    }

    public List<Voucher> findVoucherByCustomer(UUID customerId) {
        customerService.findCustomerById(customerId);
        return walletRepository.findByCustomerId(customerId).stream()
                .map(wallet -> voucherService.findVoucherById(wallet.getVoucherId()))
                .toList();
    }

    public List<Customer> findCustomerByVoucher(UUID voucherId) {
        voucherService.findVoucherById(voucherId);
        return walletRepository.findByVoucherId(voucherId).stream()
                .map(wallet -> customerService.findCustomerById(wallet.getCustomerId()))
                .toList();
    }

    @Transactional(readOnly = false)
    public Wallet giveVoucherToCustomer(UUID customerId, UUID voucherId) {
        checkBothIdExists(customerId, voucherId);
        final boolean walletExists = walletRepository.existsByCustomerIdAndVoucherId(customerId, voucherId);
        if (walletExists) throw new IllegalArgumentException(ErrorMessage.VOUCHER_ALREADY_EXISTS_MESSAGE.getMessage());
        return walletRepository.save(new Wallet(UUID.randomUUID(), customerId, voucherId));
    }

    @Transactional(readOnly = false)
    public void deleteVoucherFromCustomer(UUID customerId, UUID voucherId) {
        checkBothIdExists(customerId, voucherId);
        final boolean walletExists = walletRepository.existsByCustomerIdAndVoucherId(customerId, voucherId);
        if (!walletExists) throw new NoSuchElementException(ErrorMessage.WALLET_NOT_FOUND_MESSAGE.getMessage());
        walletRepository.delete(customerId, voucherId);
    }

    private void checkBothIdExists(UUID customerId, UUID voucherId) {
        customerService.findCustomerById(customerId);
        voucherService.findVoucherById(voucherId);
    }
}
