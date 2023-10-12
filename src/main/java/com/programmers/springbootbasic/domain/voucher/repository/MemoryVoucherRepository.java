package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@NoArgsConstructor
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMemory = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMemory.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMemory.values()
                .stream()
                .toList();
    }
}
