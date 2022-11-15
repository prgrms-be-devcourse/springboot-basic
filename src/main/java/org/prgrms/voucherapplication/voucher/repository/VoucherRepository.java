package org.prgrms.voucherapplication.voucher.repository;

import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository {
    void save(Voucher voucher);

    String findAll();
}
