package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
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
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId){
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public int update(Voucher voucher){
        storage.put(voucher.getId(), voucher);
        return 1;
    }

    @Override
    public int deleteById(UUID voucherId){
        Voucher deletedVoucher = storage.remove(voucherId);
        return deletedVoucher == null ? 0 : 1;
    }
}
