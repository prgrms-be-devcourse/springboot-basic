package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    @Override
    public Voucher save(Voucher voucher) {

        return null;
    }
}
