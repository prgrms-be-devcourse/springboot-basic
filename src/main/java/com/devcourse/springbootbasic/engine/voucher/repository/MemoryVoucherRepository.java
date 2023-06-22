package com.devcourse.springbootbasic.engine.voucher.repository;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream().toList();
    }

    public void setVoucherMap(Map<UUID, Voucher> voucherMap) {
        this.voucherMap = voucherMap;
    }
}
