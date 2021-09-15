package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> memory;

    public MemoryVoucherRepository() {
        memory = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(memory.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return List.of(memory.values().toArray(new Voucher[]{}));
    }

    @Override
    public int insert(Voucher voucher) {
        memory.put(voucher.getVoucherId(), voucher);
        return memory.size();
    }
}
