package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.model.wallet.VoucherWallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    void save(Voucher voucher);
    List<Voucher> findAll();

    Optional<Voucher> findByCustomerId(UUID customerId);

    VoucherWallet findVoucherWallet(UUID customerId);

    void deleteAll();

    void deleteVoucher(UUID voucherId);

    void deleteVoucher(UUID customerId, UUID voucherId);

    List<Voucher> findVouchersByType(VoucherType voucherType);
}
