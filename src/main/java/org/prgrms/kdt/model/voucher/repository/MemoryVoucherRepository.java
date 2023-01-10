package org.prgrms.kdt.model.voucher.repository;

import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public Voucher update(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void insert(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getAllStoredVoucher() {
        List<Voucher> storedVoucher = new ArrayList<>();
        for (UUID voucherId : repository.keySet()) {
            storedVoucher.add(repository.get(voucherId));
        }

        return storedVoucher;
    }

    @Override
    public UUID remove(UUID voucherId) {
        Voucher remove = repository.get(voucherId);

        if (Objects.equals(remove, null)) {
            throw new IllegalArgumentException("삭제할 바우처가 없습니다.");
        }

        repository.remove(voucherId);
        return voucherId;
    }

    @Override
    public void clear() {
        repository.clear();
    }
}
