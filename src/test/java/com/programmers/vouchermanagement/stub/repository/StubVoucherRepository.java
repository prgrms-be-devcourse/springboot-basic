package com.programmers.vouchermanagement.stub.repository;

import com.programmers.vouchermanagement.domain.voucher.tmp.Voucher;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import com.programmers.vouchermanagement.stub.util.StubUuidProvider;
import com.programmers.vouchermanagement.util.IdProvider;

import java.util.*;
import java.util.stream.Collectors;

public class StubVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage;
    private final IdProvider<UUID> idProvider;

    public StubVoucherRepository(List<Voucher> vouchers) {
        this.storage = new HashMap<>();
        this.idProvider = new StubUuidProvider(UUID.randomUUID());
        vouchers.forEach(voucher -> storage.put(voucher.getId(), voucher));
    }

    @Override
    public void save(Voucher voucher) {
        voucher.setId(idProvider.generateId());
        storage.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream()
                .collect(Collectors.toList());
    }
}
