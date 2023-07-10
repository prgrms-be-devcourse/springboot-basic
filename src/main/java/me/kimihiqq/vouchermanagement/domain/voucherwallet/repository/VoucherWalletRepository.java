package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import me.kimihiqq.vouchermanagement.domain.voucherwallet.VoucherWallet;

import java.util.Set;
import java.util.UUID;

public interface VoucherWalletRepository {
    void addVoucherToWallet(VoucherWallet voucherWallet);
    void removeVoucherFromWallet(VoucherWallet voucherWallet);
    Set<VoucherWallet> findVoucherWalletsByCustomerId(UUID customerId);
    void deleteByCustomerId(UUID customerId);
    Set<VoucherWallet> findVoucherWalletsByVoucherId(UUID voucherId);
}