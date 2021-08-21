package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("prod")
public class InMemoryVoucherRepository implements VoucherRepository {

    private AtomicLong sequencer = new AtomicLong(0);
    private Map<Long, Voucher> db = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(InMemoryVoucherRepository.class);

    @Override
    public Voucher save(Voucher voucher) {
        long id = sequencer.getAndAdd(1);
        voucher.setId(id);
        db.put(id, voucher);
        log.debug("Saved voucher '{}' to database.", voucher);
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
    public void persistVouchers() {

    }
}
