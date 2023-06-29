package com.example.springbootbasic.voucher;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final List<Voucher> storage = new ArrayList<>();

    @Override
    public void save(Voucher voucher) {
        storage.add(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(storage);
    }
}
