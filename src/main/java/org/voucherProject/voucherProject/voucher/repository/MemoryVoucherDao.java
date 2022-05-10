package org.voucherProject.voucherProject.voucher.repository;

import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherDao implements VoucherDao {

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
    public List<Voucher> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findByCreatedAtBetween(String date1, String date2) {
        return null;
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

    @Override
    public void deleteOneByCustomerId(UUID customerId, UUID voucherId) {

    }

}
