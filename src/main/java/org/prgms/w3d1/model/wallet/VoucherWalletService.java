package org.prgms.w3d1.model.wallet;

import java.util.Optional;
import java.util.UUID;

public class VoucherWalletService {

    private VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public VoucherWallet findByCustomerId(UUID customerId) {
        var voucherWallet = voucherWalletRepository.findByCustomerId(customerId);

        if(voucherWallet.isEmpty()) {
            throw new RuntimeException("There is no VoucherWallet, customerId: " + customerId.toString());
        }

        return voucherWallet.get();
    }

    public VoucherWallet findById(UUID voucherWalletId) {
        var voucherWallet = voucherWalletRepository.findById(voucherWalletId);

        if(voucherWallet.isEmpty()) {
            throw new RuntimeException("There is no VoucherWallet, voucherWalletId : " + voucherWallet);
        }

        return voucherWallet.get();
    }

    void insert(UUID voucherWalletId, UUID customerId) {
        voucherWalletRepository.insert(voucherWalletId, customerId);
    }

    void deleteAll() {
        voucherWalletRepository.deleteAll();
    }
}
