package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.Voucher;

import java.util.*;


public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Map<UUID, Voucher> findAllVoucher();
}
