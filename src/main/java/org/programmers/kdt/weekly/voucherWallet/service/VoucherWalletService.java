package org.programmers.kdt.weekly.voucherWallet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucherWallet.repository.VoucherWalletRepository;
import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
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

    public VoucherWallet insertWallet(UUID customerId, UUID voucherId, LocalDateTime expirationAt) {
        var voucherWallet = new VoucherWallet(UUID.randomUUID(), customerId, voucherId,
            LocalDateTime.now(), expirationAt);
        voucherWalletRepository.insert(voucherWallet);

        return voucherWallet;
    }

    public List<VoucherWallet> getVoucherWallet(UUID customerId) {
        var voucherWallet = voucherWalletRepository.findAll(customerId);

        return voucherWallet;
    }

    public Optional<UUID> deleteWalletById(UUID customerId, UUID walletId) {
        var deleteWalletId = voucherWalletRepository.deleteById(customerId, walletId);

        return deleteWalletId;
    }
}
