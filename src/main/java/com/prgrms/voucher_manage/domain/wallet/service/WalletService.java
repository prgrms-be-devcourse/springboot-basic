package com.prgrms.voucher_manage.domain.wallet.service;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;
import com.prgrms.voucher_manage.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.prgrms.voucher_manage.base.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public Wallet save(Wallet wallet) {
        if (walletRepository.findByIds(wallet.getCustomerId(), wallet.getVoucherId()).isPresent())
            throw new RuntimeException(WALLET_ALREADY_EXISTS.getMessage());
        return walletRepository.save(wallet);
    }

    public List<Voucher> findByCustomerId(UUID customerId) {
        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);
        if (wallets.isEmpty())
            throw new RuntimeException(WALLET_CUSTOMER_NOT_EXISTS.getMessage());

        return wallets.stream()
                .map(w -> voucherService.getVoucherById(w.getVoucherId()))
                .collect(Collectors.toList());
    }

    public List<Customer> findByVoucherId(UUID voucherId) {
        List<Wallet> wallets = walletRepository.findByVoucherId(voucherId);
        if (wallets.isEmpty())
            throw new RuntimeException(WALLET_VOUCHER_NOT_EXISTS.getMessage());

        return wallets.stream()
                .map(w -> customerService.findById(w.getCustomerId()))
                .collect(Collectors.toList());
    }

    public void delete(Wallet wallet) {
        if (walletRepository.findByIds(wallet.getCustomerId(), wallet.getVoucherId()).isEmpty())
            throw new RuntimeException(WALLET_DELETE_NOT_EXISTS.getMessage());
        walletRepository.delete(wallet);
    }
}
