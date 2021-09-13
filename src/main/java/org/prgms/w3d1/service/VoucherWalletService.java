package org.prgms.w3d1.service;

import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.repository.VoucherRepository;
import org.prgms.w3d1.repository.VoucherWalletRepository;

import java.util.UUID;

public class VoucherWalletService {

    private final VoucherWalletRepository voucherWalletRepository;
    private final VoucherRepository voucherRepository;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository, VoucherRepository voucherRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
        this.voucherRepository = voucherRepository;
    }

    public VoucherWallet findByCustomerId(UUID customerId) {
        var voucherWallet = voucherWalletRepository.findByCustomerId(customerId);

        if(voucherWallet.isEmpty()) {
            throw new RuntimeException("There is no VoucherWallet, customerId: " + customerId.toString());
        }

        setVoucherList(voucherWallet.get());
        return voucherWallet.get();
    }

    public VoucherWallet findById(UUID voucherWalletId) {
        var voucherWallet = voucherWalletRepository.findById(voucherWalletId);

        if(voucherWallet.isEmpty()) {
            throw new RuntimeException("There is no VoucherWallet, voucherWalletId : " + voucherWallet);
        }

        setVoucherList(voucherWallet.get());
        return voucherWallet.get();
    }

    void insert(VoucherWallet voucherWallet) {
        voucherWalletRepository.insert(voucherWallet);
    }

    void deleteAll() {
        voucherWalletRepository.deleteAll();
    }

    private void setVoucherList(VoucherWallet voucherWallet) {
        if (voucherWallet.getVoucherList() != null) {
           throw new RuntimeException("이미 지갑이 있습니다");
        }

        var voucherList = voucherRepository.findByVoucherWalletId(voucherWallet.getVoucherWalletId());
        voucherWallet.setVoucherList(voucherList);
    }
}
