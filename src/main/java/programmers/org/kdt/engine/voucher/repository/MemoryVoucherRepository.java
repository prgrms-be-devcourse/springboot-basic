package programmers.org.kdt.engine.voucher.repository;

import java.util.Set;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import programmers.org.kdt.engine.voucher.type.Voucher;

@Repository
@Profile({"default", "test"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public Set<Map.Entry<UUID, Voucher>> getAllEntry() {
        return storage.entrySet();
    }
}
