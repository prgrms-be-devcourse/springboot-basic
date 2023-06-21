package com.programmers.voucher.repository;

import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final HashMap<String, Voucher> voucherList = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<String, Voucher> findAll() {
        return voucherList;
    }
}
