package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class JDBCVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> find() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
