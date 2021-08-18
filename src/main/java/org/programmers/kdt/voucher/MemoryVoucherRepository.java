package org.programmers.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> repository = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(repository.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        this.repository.put(voucherId, voucher);
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> list = new ArrayList<>();

        Set<UUID> keys = this.repository.keySet();
        for (UUID key : keys) {
            list.add(this.repository.get(key));
        }

        return list;
    }
}
