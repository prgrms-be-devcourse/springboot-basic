package org.promgrammers.springbootbasic.domain.voucher.repository.impl;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.promgrammers.springbootbasic.exception.repository.DuplicateIDException;
import org.promgrammers.springbootbasic.exception.repository.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (storage.containsKey(voucher.getVoucherId())) {
            throw new DuplicateIDException("이미 저장되어 있는 Voucher ID 입니다.");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher != null) {
            voucher.assignCustomerId(customerId);
        }
    }

    @Override
    public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher != null && voucher.getCustomerId() != null && voucher.getCustomerId().equals(customerId)) {
            voucher.assignCustomerId(null);
        }
    }

    @Override
    public List<Voucher> findAllByCustomerId(UUID customerId) {
        return storage.values().stream()
                .filter(voucher -> voucher.getCustomerId() != null && voucher.getCustomerId().equals(customerId))
                .toList();
    }

    @Override
    public Voucher update(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        if (!storage.containsKey(voucher)) {
            throw new IllegalArgumentException("해당 Voucher ID가 존재하지 않습니다. => " + voucherId);
        }
        storage.put(voucherId, voucher);
        return voucher;
    }

    @Override
    public void deleteAll() {
        this.storage.clear();
    }

    @Override
    public void deleteById(UUID voucherId) {
        if (!storage.containsKey(voucherId)) {
            throw new EntityNotFoundException("삭제할 Voucher가 존재하지 않습니다. Voucher ID: " + voucherId);
        }
        storage.remove(voucherId);
    }
}