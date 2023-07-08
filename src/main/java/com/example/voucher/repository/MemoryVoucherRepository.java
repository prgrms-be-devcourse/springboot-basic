package com.example.voucher.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import com.example.voucher.domain.Voucher;

@Component
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> inMemoryDataBase = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        inMemoryDataBase.put(voucherId, voucher);

        return inMemoryDataBase.get(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(inMemoryDataBase.values());
    }

}
