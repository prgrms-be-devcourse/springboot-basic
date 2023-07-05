package org.weekly.weekly.voucher.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.weekly.weekly.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("!dev")
@Repository
public class JdbcVoucherRepository implements VoucherRepository{
    @Override
    public Voucher insert(Voucher voucher) {
        return null;
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
