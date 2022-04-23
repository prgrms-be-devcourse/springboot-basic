package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Voucher> findAll() {
        return null;
    }
}
