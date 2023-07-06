package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        log.info("Voucher saved: {}", voucher.getVoucherId());
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = store.get(voucherId);
        if (voucher != null) {
            log.info("Voucher found: {}", voucherId);
            return Optional.of(voucher);
        }
        log.info("Voucher not found: {}", voucherId);
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>(store.values());
        log.info("All vouchers retrieved");
        return vouchers;
    }

    @Override
    public void deleteById(UUID voucherId) {
        if (store.containsKey(voucherId)) {
            store.remove(voucherId);
            log.info("Voucher deleted: {}", voucherId);
        } else {
            log.info("Voucher not found for deletion: {}", voucherId);
        }
    }
}
