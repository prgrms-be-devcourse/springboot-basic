package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.domain.Voucher;
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

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        System.out.println("-------------dev MemoryVoucherRepository------------------");
        return storage.entrySet()
                .stream()
                .map(o -> o.getValue()).toList();
    }

    @Override
    public Voucher save(Voucher voucher) {

        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
