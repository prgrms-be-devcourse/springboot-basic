package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID,Voucher>vouchers;

    public MemoryVoucherRepository() {
        this.vouchers = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher save(Voucher voucher) {
        return vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public void update(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            Voucher voucher = vouchers.get(id);
            return Optional.of(voucher);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }

    @Override
    public void deleteById(UUID id) {
        if (vouchers.containsKey(id)) {
            vouchers.remove(id);
        }
    }

}
