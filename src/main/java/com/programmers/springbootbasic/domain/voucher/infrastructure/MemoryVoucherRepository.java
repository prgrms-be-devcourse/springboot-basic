package com.programmers.springbootbasic.domain.voucher.infrastructure;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final ConcurrentHashMap<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }
}
