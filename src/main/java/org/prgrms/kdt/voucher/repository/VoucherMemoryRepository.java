package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.Voucher;

import java.util.*;

public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers = new HashMap<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher item = vouchers.get(voucherId);
        if (item == null)
            return Optional.empty();
        return Optional.of(item);
    }

    @Override
    public List<Voucher> find() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public Voucher insert(Voucher voucher) { // TODO: Question. create 하면 반환값은 어떤걸하면 좋나요?
        vouchers.put(voucher.getVoucherId(), voucher);
        return vouchers.get(voucher.getVoucherId());
    }
}
