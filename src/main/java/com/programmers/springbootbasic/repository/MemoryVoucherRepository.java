package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<String, Voucher> memory = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        memory.put(String.valueOf(voucher.getVoucherId()), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        return Optional.ofNullable(memory.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memory.values());
    }

}
