package com.prgms.voucher.voucherproject.repository.voucher;

import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

}
