package me.kimihiqq.vouchermanagement.domain.voucherwallet.service;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.VoucherWallet;

import java.util.Set;
import java.util.UUID;

public interface VoucherWalletService {
    Set<Voucher> findVouchersByCustomerId(UUID customerId);
    void addVoucherToWallet(UUID customerId, UUID voucherId);
    void removeVoucherFromWallet(UUID customerId, UUID voucherId);
    Set<UUID> findCustomerIdsByVoucherId(UUID voucherId);

//    void deleteVoucherWalletByCustomerId(UUID customerId);
//    VoucherWallet createVoucherWalletForCustomer(UUID customerId);


}