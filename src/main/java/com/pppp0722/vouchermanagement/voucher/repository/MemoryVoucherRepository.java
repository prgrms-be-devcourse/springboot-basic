package com.pppp0722.vouchermanagement.voucher.repository;


import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private List<Voucher> storage = new LinkedList<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.add(voucher);
        return null;
    }

    @Override
    public List<Voucher> getVoucherList() {
        return storage;
    }

}
