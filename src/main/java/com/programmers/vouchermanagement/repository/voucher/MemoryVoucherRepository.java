package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.util.IdProvider;
import com.programmers.vouchermanagement.util.UuidProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

//@Repository
//@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage;
    private final IdProvider<UUID> idProvider;

    public MemoryVoucherRepository() {
        this.storage = new HashMap<>();
        this.idProvider = new UuidProvider();
    }

    @Override
    public void save(Voucher voucher) {
        voucher.setId(idProvider.generateId());
        storage.put(voucher.getId(), voucher);
    }

    @Override
    public void saveAll(List<Voucher> vouchers) {
        unsupport();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream()
                .collect(Collectors.toList());
    }

    @Override
    public void updateById(UUID id, Voucher voucher) {
        unsupport();
    }

    @Override
    public void deleteById(UUID id) {
        unsupport();
    }

    @Override
    public void deleteAll() {
        unsupport();
    }

    private void unsupport() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
