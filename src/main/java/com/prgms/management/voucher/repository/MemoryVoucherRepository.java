package com.prgms.management.voucher.repository;

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
            return null;
        }
        return voucher.get();
    }
    
    @Override
    public List<Voucher> findByType(VoucherType type) {
        return null;
    }
    
    @Override
    public List<Voucher> findByDate(Timestamp start, Timestamp end) {
        return null;
    }
    
    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, Timestamp start, Timestamp end) {
        return null;
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
