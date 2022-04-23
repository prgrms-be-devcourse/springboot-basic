package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Qualifier("jdbc")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void delete(UUID voucherId) {

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
