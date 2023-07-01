package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherRegistry;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new TreeMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public VoucherRegistry getAllVoucher() {
        return new VoucherRegistry(storage.values()
                .stream()
                .toList());
    }
}
