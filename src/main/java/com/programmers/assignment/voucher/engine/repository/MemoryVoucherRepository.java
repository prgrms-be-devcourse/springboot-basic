package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        repository.forEach((id, voucher) -> voucherList.add(voucher));
        return voucherList;
    }

    @Override
    public void insert(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
    }
}
