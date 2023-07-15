package com.programmers.springmission.voucher.repository;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.NotFoundException;
import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class InMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByPolicy(VoucherType voucherType) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.getVoucherPolicy().getVoucherType() == voucherType)
                .toList();
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .toList();
    }

    @Override
    public void updateAmount(Voucher voucher) {
        update(voucher);
    }

    @Override
    public void updateCustomer(Voucher voucher) {
        update(voucher);
    }

    private void update(Voucher voucher) {
        if (!storage.containsKey(voucher.getVoucherId())) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_VOUCHER);
        }
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteById(UUID voucherId) {
        if (!storage.containsKey(voucherId)) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_VOUCHER);
        }
        storage.remove(voucherId);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
