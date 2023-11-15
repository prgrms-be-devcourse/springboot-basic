package org.prgrms.kdtspringdemo.wallet.service;

import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.dto.WalletViewDto;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet create(UUID customerId) {
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId);
        return walletRepository.insert(wallet);
    }

    public Optional<WalletViewDto> findById(UUID walletId){
        return Optional.of(new WalletViewDto(walletRepository.findById(walletId).get()));
    }

    public List<VoucherViewDto> findVouchersById(UUID customerId) {
        List<VoucherViewDto> voucherViewDtoList = new ArrayList<>();
        walletRepository.findVouchersByCustomerId(customerId).forEach(voucher -> voucherViewDtoList.add(new VoucherViewDto(voucher)));
        return voucherViewDtoList;
    }

    public void addVoucherByCustomerId(UUID walletId, UUID customerId, UUID voucherId) {
        walletRepository.addVoucherByCustomerId(walletId, customerId, voucherId);
    }

    public void deleteVoucherByVoucherId(UUID customerId, UUID voucherId) {
        walletRepository.deleteVoucherByVoucherId(customerId, voucherId);
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }
    public List<WalletViewDto> getWalletViewDtoList() {
        List<Wallet> walletList = this.findAll();
        List<WalletViewDto> walletViewDtoList = new ArrayList<>();
        walletList.forEach(wallet -> walletViewDtoList.add(new WalletViewDto(wallet)));
        return walletViewDtoList;
    }

    public void deleteById(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public void deleteAll() {
        walletRepository.deleteAll();
    }
}
