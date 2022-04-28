//package org.programmers.kdt.weekly.voucherWallet.repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
//
//public interface VoucherWalletRepository {
//
//    VoucherWallet insert(VoucherWallet voucherWallet);
//
//    List<VoucherWallet> findAll();
//
//    List<VoucherWallet> findByCustomerId(UUID customerId);
//
//    Optional<VoucherWallet> findByWalletId(UUID walletId);
//
//    Optional<UUID> deleteById(UUID customerId, UUID walletId);
//
//    void deleteAllByCustomerId(UUID customerId);
//}