package org.prgrms.springbootbasic.repository;


import org.prgrms.springbootbasic.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {

    void insert(Voucher voucher);

    Optional<Voucher> findById(UUID VoucherId);

    Map<UUID, Voucher> findAll();
}
