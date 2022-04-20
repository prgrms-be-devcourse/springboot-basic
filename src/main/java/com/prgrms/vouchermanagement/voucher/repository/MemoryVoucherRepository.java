package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }
}
