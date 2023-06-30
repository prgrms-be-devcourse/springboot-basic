package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository("dev")
@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findByID(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
