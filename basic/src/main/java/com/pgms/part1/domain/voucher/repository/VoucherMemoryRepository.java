package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;

import java.util.*;
import java.util.stream.Collectors;

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
