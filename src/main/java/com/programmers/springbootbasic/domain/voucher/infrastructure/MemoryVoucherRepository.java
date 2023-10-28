package com.programmers.springbootbasic.domain.voucher.infrastructure;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final ConcurrentHashMap<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public int deleteById(UUID id) {
        return vouchers.remove(id) == null ? 0 : 1;
    }

    @Override
    public int update(Voucher voucher) {
        return vouchers.put(voucher.getId(), voucher) == null ? 0 : 1;
    }
}
