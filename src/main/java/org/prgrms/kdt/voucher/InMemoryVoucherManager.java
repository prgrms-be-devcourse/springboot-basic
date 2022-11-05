package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryVoucherManager implements VoucherManager {
    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }
}
