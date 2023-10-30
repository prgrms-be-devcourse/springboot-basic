package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.CreateWalletRequestDto;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class WalletService {
    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public void createWallet(CreateWalletRequestDto request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Not found customer"));
        Voucher voucher = voucherRepository.findById(request.getVoucherId())
                .orElseThrow(() -> new IllegalArgumentException("Not found voucher"));

        boolean foundWallet = walletRepository.findByCustomerIdAndVoucherId(customer.getId(), voucher.getId()).isPresent();
        if (foundWallet) {
            throw new IllegalArgumentException("Already exist wallet");
        }

        walletRepository.save(new Wallet(customer, voucher));
    }

    @Transactional(readOnly = true)
    public List<Wallet> getWallets(GetWalletsRequestDto request) {
        return walletRepository.findAll(request);
    }

    public void deleteWallet(int id, UUID customerId) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found wallet"));

        if (!wallet.getCustomer().getId().equals(customerId)) {
            throw new SecurityException("Forbidden");
        }

        walletRepository.deleteById(id);
    }
}
