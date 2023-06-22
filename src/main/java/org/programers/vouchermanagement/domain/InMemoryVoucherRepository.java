package org.programers.vouchermanagement.domain;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> VOUCHER_REPOSITORY = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        return VOUCHER_REPOSITORY.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(VOUCHER_REPOSITORY.get(id));
    }
}
