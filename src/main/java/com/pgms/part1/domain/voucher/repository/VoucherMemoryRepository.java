package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("local")
@Repository
public class VoucherMemoryRepository implements VoucherRepository{
    private final static Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public List<Voucher> list() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void add(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
    }
}
