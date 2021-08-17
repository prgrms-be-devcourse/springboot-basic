package org.programmers.kdt.voucher;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoryVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> repository = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(repository.get(voucherId));
    }

    @Override
    public void addVoucher(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        this.repository.put(voucherId, voucher);
    }

    @Override
    public void listAllVouchers() {
        Set<UUID> keys = this.repository.keySet();
        for (UUID key : keys) {
            System.out.println(this.repository.get(key));
        }
    }
}
