package org.prgms.voucheradmin.domain.voucherwallet.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;

public interface VoucherWalletRepository {
    VoucherWallet create(VoucherWallet voucherWallet);

    List<VoucherWallet> findAll();

    Optional<VoucherWallet> findByCustomerIdAndVoucherId(UUID customerId, UUID voucherId);

    void deleteVoucherWallet(VoucherWallet voucherWallet);
}
