package org.prgrms.kdt.voucher.repository;

import java.util.List;
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
    public List<Voucher> findAll() {
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
