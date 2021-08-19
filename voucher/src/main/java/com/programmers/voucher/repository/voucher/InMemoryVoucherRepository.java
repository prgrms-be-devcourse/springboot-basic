package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    AtomicLong sequencer = new AtomicLong(1);
    Map<Long, Voucher> db = new HashMap<>();

    @Override
    public Voucher save(String name, Voucher.type type) {
        long id = sequencer.getAndAdd(1);
        Voucher voucher = new Voucher(id, name, type);
        db.put(id, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void loadVouchers() {

    }

    @Override
    public void saveVouchers() {

    }
}
