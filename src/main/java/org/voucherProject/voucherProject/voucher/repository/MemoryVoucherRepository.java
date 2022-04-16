package org.voucherProject.voucherProject.voucher.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.voucher.entity.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@Primary
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher){
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new RuntimeException("동일한 아이디가 존재합니다.");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
