package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> memoryCache = new HashMap<>();
    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memoryCache.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        memoryCache.putIfAbsent(voucher.getUuid(), voucher);
        return voucher;
    }
}
