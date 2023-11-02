package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;
import com.programmers.vouchermanagement.util.IdProvider;
import com.programmers.vouchermanagement.util.UuidProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Deprecated
@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage;
    private final IdProvider<UUID> idProvider;

    public MemoryVoucherRepository() {
        this.storage = new HashMap<>();
        this.idProvider = new UuidProvider();
    }

    @Override
    public UUID save(Voucher voucher) {
        UUID id = idProvider.generateId();
        voucher.setId(id);
        storage.put(voucher.getId(), voucher);
        return id;
    }

    @Override
    public void saveAll(List<Voucher> vouchers) {
        unsupported();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll(GetVouchersRequestDto request) {
        return storage.values().stream()
                .collect(Collectors.toList());
    }

    @Override
    public void update(Voucher voucher) {
        unsupported();
    }

    @Override
    public void deleteById(UUID id) {
        unsupported();
    }

    @Override
    public void deleteAll() {
        unsupported();
    }

    private void unsupported() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
