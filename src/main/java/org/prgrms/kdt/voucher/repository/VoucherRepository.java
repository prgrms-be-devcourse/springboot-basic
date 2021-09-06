package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;

import java.util.*;

public interface VoucherRepository {
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    Voucher update(Voucher voucher);
    void deleteAll();
    Map<UUID, Voucher> getStorage();
}
