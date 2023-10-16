package org.prgms.springbootbasic.repository;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.VoucherType;
import org.prgms.springbootbasic.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev", "prod", "local", "test"})
@Slf4j
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
        log.info("voucher = {}", voucher);
        mem.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Voucher create(Voucher voucher) {
        mem.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
