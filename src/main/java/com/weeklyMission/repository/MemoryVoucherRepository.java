package com.weeklyMission.repository;

import com.weeklyMission.domain.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage;

    public MemoryVoucherRepository(){
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getVoucherList() {
        return new ArrayList<>(storage.values());
    }
}
