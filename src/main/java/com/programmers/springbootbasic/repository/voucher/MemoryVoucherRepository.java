package com.programmers.springbootbasic.repository.voucher;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {
    Map<UUID, Voucher> storage = new HashMap<>();

    public void save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
    }

    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
