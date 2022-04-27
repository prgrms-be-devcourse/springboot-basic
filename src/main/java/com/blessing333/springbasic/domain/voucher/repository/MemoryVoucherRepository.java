package com.blessing333.springbasic.domain.voucher.repository;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Profile("test")
public class MemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public void update(Voucher voucher) {
        voucherStore.remove(voucher.getVoucherId());
        insert(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(voucherStore.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherStore.values());
    }

    @Override
    public void insert(Voucher voucher) {
        voucherStore.put(voucher.getVoucherId(),voucher);
    }

    @Override
    public void deleteById(UUID id) {
        voucherStore.remove(id);
    }

    @Override
    public void deleteAll() {
        voucherStore.clear();
    }

    @Override
    public List<Voucher> findByVoucherType(Voucher.VoucherType type) {
        return voucherStore.values().stream().filter(voucher -> voucher.getVoucherType() == type).collect(Collectors.toList());
    }
}
