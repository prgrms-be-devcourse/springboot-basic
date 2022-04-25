package com.prgms.management.voucher.repository;

import com.prgms.management.common.exception.FindFailException;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher findById(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(storage.get(voucherId));
        if (voucher.isEmpty()) {
            throw new FindFailException("찾는 ID에 대한 바우처가 없습니다.");
        }
        return voucher.get();
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return storage.values().stream().filter(voucher -> (voucher.getType() == type)).toList();
    }

    @Override
    public List<Voucher> findByDate(Timestamp start, Timestamp end) {
        return storage.values().stream().filter(voucher -> (voucher.getCreatedAt().after(start) && voucher.getCreatedAt().before(end))).toList();
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, Timestamp start, Timestamp end) {
        return storage.values().stream().filter(voucher -> (voucher.getCreatedAt().after(start) && voucher.getCreatedAt().before(end) && voucher.getType() == type)).toList();
    }

    @Override
    public List<Voucher> findAll() {
        if (storage.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public void removeById(UUID voucherId) {
        storage.remove(voucherId);
    }
}
