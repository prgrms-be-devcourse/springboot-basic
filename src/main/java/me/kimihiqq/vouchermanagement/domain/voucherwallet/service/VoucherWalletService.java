package me.kimihiqq.vouchermanagement.domain.voucherwallet.service;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.VoucherWallet;

import java.util.Set;
import java.util.UUID;

public interface VoucherWalletService {
    VoucherWallet createVoucherWalletForCustomer(UUID customerId);
    Set<Voucher> findVouchersByCustomerId(UUID customerId);
    void deleteVoucherWalletByCustomerId(UUID customerId);
    void addVoucherToWallet(UUID customerId, UUID voucherId);
    void removeVoucherFromWallet(UUID customerId, UUID voucherId);
}