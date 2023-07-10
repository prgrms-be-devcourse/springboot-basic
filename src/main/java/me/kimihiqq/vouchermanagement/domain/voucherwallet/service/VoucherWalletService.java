package me.kimihiqq.vouchermanagement.domain.voucherwallet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.service.VoucherService;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.repository.VoucherWalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile({"db", "test"})
@Slf4j
@RequiredArgsConstructor
@Service
public class VoucherWalletService {
    private final VoucherWalletRepository voucherWalletRepository;
    private final VoucherService voucherService;


    public Set<Voucher> findVouchersByCustomerId(UUID customerId) {
        Set<UUID> voucherIds = voucherWalletRepository.findVoucherIdsByCustomerId(customerId);
        return voucherIds.stream()
                .map(voucherService::findVoucherById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }


    public void addVoucherToWallet(UUID customerId, UUID voucherId) {
        voucherWalletRepository.addVoucherToWallet(customerId, voucherId);
    }

    public void removeVoucherFromWallet(UUID customerId, UUID voucherId) {
        voucherWalletRepository.removeVoucherFromWallet(customerId, voucherId);
    }

    public Set<UUID> findCustomerIdsByVoucherId(UUID voucherId) {
        return voucherWalletRepository.findCustomerIdsByVoucherId(voucherId);
    }
}