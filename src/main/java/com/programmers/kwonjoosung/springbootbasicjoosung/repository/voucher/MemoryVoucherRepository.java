package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public boolean insert(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher) != null;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

}
