package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoryRepository implements Repository {
    Map<UUID, Voucher> storage = new HashMap<>();

    public void save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
    }

    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
