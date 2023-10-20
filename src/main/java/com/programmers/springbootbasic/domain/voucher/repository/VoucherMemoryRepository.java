package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
@NoArgsConstructor
public class VoucherMemoryRepository implements VoucherRepository {
    private Map<UUID, Voucher> voucherMemory = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMemory.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMemory.values()
                .stream()
                .toList();
    }

    @Override
    public void deleteAll() {
        voucherMemory = new HashMap<>();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(voucherMemory.get(voucherId));
    }

    @Override
    public void update(Voucher voucher) {
        voucherMemory.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void delete(Voucher voucher) {
        voucherMemory.remove(voucher.getVoucherId());
    }
}
