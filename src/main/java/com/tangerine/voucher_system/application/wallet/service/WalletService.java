package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.customer.service.mapper.CustomerServiceMapper;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.repository.VoucherRepository;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.voucher.service.mapper.VoucherServiceMapper;
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
    private final WalletServiceMapper walletMapper;
    private final CustomerServiceMapper customerMapper;
    private final VoucherServiceMapper voucherMapper;

    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, CustomerRepository customerRepository, WalletServiceMapper walletMapper, CustomerServiceMapper customerMapper, VoucherServiceMapper voucherMapper) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.walletMapper = walletMapper;
        this.customerMapper = customerMapper;
        this.voucherMapper = voucherMapper;
    }

    public UUID createWallet(WalletParam param) {
        walletRepository.insert(walletMapper.paramToDomain(param));
        return param.walletId();
    }

    public UUID updateWallet(WalletParam param) {
        walletRepository.update(walletMapper.paramToDomain(param));
        return param.walletId();
    }

    public UUID deleteWalletById(UUID walletId) {
        walletRepository.deleteById(walletId);
        return walletId;
    }

    private List<WalletResult> findWalletsByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId)
                .stream()
                .map(walletMapper::domainToResult)
                .toList();
    }

    public List<VoucherResult> findVouchersByCustomerId(UUID customerId) {
        return voucherMapper.domainsToResults(
                findWalletsByCustomerId(customerId)
                        .stream()
                        .map(wallet -> voucherRepository.findById(wallet.voucherId())
                                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())))
                        .toList());
    }

    private List<WalletResult> findWalletsByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId)
                .stream()
                .map(walletMapper::domainToResult)
                .toList();
    }

    public List<CustomerResult> findCustomersByVoucherId(UUID voucherId) {
        return customerMapper.domainsToResults(
                findWalletsByVoucherId(voucherId)
                        .stream()
                        .map(wallet -> customerRepository.findById(wallet.customerId())
                                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())))
                        .toList());
    }

}
