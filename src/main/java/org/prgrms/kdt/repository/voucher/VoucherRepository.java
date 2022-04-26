package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;


import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> insertVoucher(Voucher voucher);

    Map<UUID, Voucher> getVoucherList();

    void deleteVoucherById(UUID voucherId);

    Optional<Voucher> getByVoucherId(UUID voucherId);

    List<Voucher> getVoucherListOwnerIdIsEmpty();

    Optional<Voucher> updateVoucherOwner(UUID voucherId, UUID customerId);

    Optional<Voucher> getVoucherNotProvided(UUID voucherId);

    void deleteAllVouchers();
}
