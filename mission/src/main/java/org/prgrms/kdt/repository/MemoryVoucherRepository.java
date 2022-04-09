package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = voucherStorage.get(voucherId);

        return Optional.ofNullable(voucher);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherStorage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<List<Voucher>> findAll() {
        return Optional.empty();
    }
}
