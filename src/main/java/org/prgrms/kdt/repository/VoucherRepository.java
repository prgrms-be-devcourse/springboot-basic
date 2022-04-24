package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.Voucher;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository<K, V> {
    Voucher insertVoucher(Voucher voucher);

    Map<K, V> getVoucherList();

    UUID deleteVoucherById(UUID voucherId);

    Voucher getByVoucherId(UUID voucherId);

    List<Voucher> getVoucherListOwnerIdIsEmpty();

    Voucher updateVoucherOwner(UUID voucherId, UUID customerId);

    Voucher getVoucherNotProvided(UUID voucherId);

    void deleteAllVouchers();
}
