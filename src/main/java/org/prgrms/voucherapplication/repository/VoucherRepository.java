package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository {
    void save(Voucher voucher);

    String findAll();
}
