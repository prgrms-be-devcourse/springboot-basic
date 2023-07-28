package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.repository.VoucherRepository;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.wallet.repository.WalletRepository;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
import com.tangerine.voucher_system.application.wallet.service.mapper.WalletServiceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public UUID createWallet(WalletParam param) {
        walletRepository.insert(WalletServiceMapper.INSTANCE.paramToDomain(param));
        return param.walletId();
    }

    public UUID updateWallet(WalletParam param) {
        walletRepository.update(WalletServiceMapper.INSTANCE.paramToDomain(param));
        return param.walletId();
    }

    public UUID deleteWalletById(UUID walletId) {
        walletRepository.deleteById(walletId);
        return walletId;
    }

    public List<WalletResult> findWalletsByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId)
                .stream()
                .map(WalletServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

    public List<VoucherResult> findVouchersByCustomerId(UUID customerId) {
        return findWalletsByCustomerId(customerId)
                .stream()
                .map(wallet -> voucherRepository.findById(wallet.voucherId())
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())))
                .map(WalletServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

    public List<WalletResult> findWalletsByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId)
                .stream()
                .map(WalletServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

    public List<CustomerResult> findCustomersByVoucherId(UUID voucherId) {
        return findWalletsByVoucherId(voucherId)
                .stream()
                .map(wallet -> customerRepository.findById(wallet.customerId())
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())))
                .map(WalletServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

}
