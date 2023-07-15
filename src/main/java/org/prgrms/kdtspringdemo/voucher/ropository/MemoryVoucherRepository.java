package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final String NOT_FOUND_VOUCHER = "바우처를 찾지 못했습니다.";
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher == null) {
            throw new NoSuchElementException(NOT_FOUND_VOUCHER);
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public Voucher update(Voucher voucher) {
        Voucher updatedVoucher = storage.putIfAbsent(voucher.getVoucherId(), voucher);
        return updatedVoucher == null ? voucher : updatedVoucher;
    }
}
