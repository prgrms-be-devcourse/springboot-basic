package org.prgrms.springbootbasic.repository;


import org.prgrms.springbootbasic.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {
    void insert(Voucher voucher);

    Optional<Voucher> findById(UUID VoucherId);

    List<Voucher> findAll();
}
