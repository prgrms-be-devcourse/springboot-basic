package org.programers.vouchermanagement.voucher.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> VOUCHER_REPOSITORY = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        VOUCHER_REPOSITORY.put(voucher.getId(), voucher);
        return VOUCHER_REPOSITORY.get(voucher.getId());
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(VOUCHER_REPOSITORY.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return VOUCHER_REPOSITORY.values().stream().toList();
    }
}
