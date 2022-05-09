package org.prgrms.voucherprgrms.voucher.repository;

import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList(storage.values());
    }

    @Override
    public List<Voucher> findByCreated(LocalDateTime start, LocalDateTime end) {
        var findList = new ArrayList<Voucher>();
        for (Voucher voucher : storage.values()) {
            if (voucher.getCreatedAt().isAfter(start) && voucher.getCreatedAt().isBefore(end.plusDays(1))
            || voucher.getCreatedAt().isEqual(start)) {
                findList.add(voucher);
            }
        }
        return findList;
    }

    @Override
    public List<Voucher> findByVoucherType(String DTYPE) {
        var findList = new ArrayList<Voucher>();
        for (Voucher voucher : storage.values()) {
            if (voucher.getDTYPE() == DTYPE) {
                findList.add(voucher);
            }
        }
        return findList;
    }

    @Override
    public void deleteById(UUID voucherId) {
        if (storage.containsKey(voucherId)) {
            storage.remove(voucherId);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
