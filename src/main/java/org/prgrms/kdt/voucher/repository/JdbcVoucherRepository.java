package org.prgrms.kdt.voucher.repository;

import java.util.Collection;
import java.util.UUID;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public Collection<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher update(UUID voucherId, long value) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

}
