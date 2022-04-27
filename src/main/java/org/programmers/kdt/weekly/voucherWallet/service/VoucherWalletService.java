package org.programmers.kdt.weekly.voucherWallet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
import org.programmers.kdt.weekly.voucherWallet.repository.VoucherWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherWalletService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherWalletService.class);

    private final VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletService(
        VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public VoucherWallet insert(UUID customerId, UUID voucherId) {
        var voucherWallet = new VoucherWallet(UUID.randomUUID(), customerId, voucherId,
            LocalDateTime.now());
        voucherWalletRepository.insert(voucherWallet);

        return voucherWallet;
    }

    public List<VoucherWallet> findById(UUID customerId) {

        return voucherWalletRepository.findByCustomerId(customerId);
    }

    public Optional<UUID> deleteById(UUID customerId, UUID walletId) {

        return voucherWalletRepository.deleteById(customerId, walletId);
    }
}
