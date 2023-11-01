package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.exception.ErrorMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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
    public Voucher findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher==null)
            throw new RuntimeException(ErrorMessage.VOUCHER_NOT_EXISTS.getMessage());
        return voucher;
    }

    @Override
    public void update(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }
}
