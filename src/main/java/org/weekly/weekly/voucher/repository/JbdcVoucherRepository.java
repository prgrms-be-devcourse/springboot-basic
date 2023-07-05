package org.weekly.weekly.voucher.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.weekly.weekly.voucher.domain.Voucher;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile({"default", "local"})
@Repository
public class JbdcVoucherRepository implements VoucherRepository{

    @Override
    public void insert(Voucher voucher) {

    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {

        return null;
    }
}
