package org.prgms.kdtspringweek1.wallet.service;

import org.prgms.kdtspringweek1.customer.service.dto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.prgms.kdtspringweek1.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet registerWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public List<FindVoucherResponseDto> searchAllVouchersByCustomerId(UUID customerId) {
        return walletRepository.findAllVouchersByCustomerId(customerId).stream()
                .map(FindVoucherResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteWalletByVoucherIdAndCustomerId(UUID voucherId, UUID customerId) {
        walletRepository.deleteByVoucherIdAndCustomerId(voucherId, customerId);
    }

    public List<FindCustomerResponseDto> searchAllCustomersByVoucherId(UUID voucherId) {
        return walletRepository.findAllCustomersByVoucherId(voucherId).stream()
                .map(FindCustomerResponseDto::new)
                .collect(Collectors.toList());
    }
}
