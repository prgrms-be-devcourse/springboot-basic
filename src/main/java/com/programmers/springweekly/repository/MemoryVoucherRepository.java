package com.programmers.springweekly.repository;

import com.programmers.springweekly.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public void saveVoucher(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> getVoucherMap() {
        return voucherMap;
    }
}
