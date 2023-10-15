package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.common.VoucherType;
import org.prgms.springbootbasic.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    ConcurrentHashMap<UUID, Voucher> mem = new ConcurrentHashMap<>();

    @Override
    public Voucher findById(UUID voucherId) {
        return Optional.ofNullable(mem.get(voucherId))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(mem.values());
    }

    @Override
    public Voucher create(VoucherType type, int val) {
        Voucher voucher = type.create(val);
        mem.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
